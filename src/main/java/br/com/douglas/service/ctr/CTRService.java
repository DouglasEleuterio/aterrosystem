package br.com.douglas.service.ctr;

import br.com.douglas.entity.entities.temp.CTR;
import br.com.douglas.entity.entities.temp.Combo;
import br.com.douglas.entity.entities.temp.DescartePorCombo;
import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.entity.entities.temp.TipoDescarte;
import br.com.douglas.entity.entities.temp.Transportador;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.ctr.CtrMapper;
import br.com.douglas.repositories.ctr.CTRRepository;
import br.com.douglas.repositories.descarteporcombo.DescartePorComboRepository;
import br.com.douglas.repositories.pagamento.PagamentoRepository;
import br.com.douglas.service.combo.ComboService;
import br.com.douglas.service.impls.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CTRService extends BaseService<CTR> {

    private final CTRRepository repository;
    private final PagamentoRepository pagamentoRepository;
    private final ComboService comboService;
    private final DescartePorComboRepository descartePorComboRepository;
    private final CtrMapper mapper;

    public CTRService(CTRRepository repository, PagamentoRepository pagamentoRepository, ComboService comboService, DescartePorComboRepository descartePorComboRepository, CtrMapper mapper) {
        super(repository);
        this.repository = repository;
        this.pagamentoRepository = pagamentoRepository;
        this.comboService = comboService;
        this.descartePorComboRepository = descartePorComboRepository;
        this.mapper = mapper;
    }

    @Transactional
    public CTR save(CTR ctr) throws DomainException {
        validate(ctr);
        ctr.setAtivo(true);
        ctr.getPagamentos().forEach(pagamento -> pagamento.setCtr(ctr));
        List<Pagamento> pagamentos = pagamentoRepository.saveAll(ctr.getPagamentos());
        ctr.setPagamentos(pagamentos);
        ctr.setTipoDescartes(ctr.getTipoDescartes());
        CTR save = repository.save(ctr);
        if(isDescarteSomenteCombo(save))
            processaBaixaCombo(ctr);
        return save;
    }

    @Transactional
    public void processaBaixaCombo(CTR ctr) {
        List<DescartePorCombo> descartePorCombos = new ArrayList<>();
        List<String> ids = retornaListaDeIdsDosTiposDescarteParaBaixa(ctr);
        Map<String, Integer> quantidadeNecessaria = retornaQuantidadeNecessariaDeCombosPorTipoDeDescarte(ids);
        List<Combo> combosABaixar = new ArrayList<>();
        Integer quantidadeABaixar;

        for(Map.Entry<String, Integer> id : quantidadeNecessaria.entrySet()){
            combosABaixar.addAll(comboService.retornaComboParaConsumirSaldoNoDescarte(quantidadeNecessaria.get(id), ctr.getTransportador().getId(), id.getKey()));
        }
        for (Combo combo : combosABaixar) {
            quantidadeABaixar = quantidadeNecessaria.get(combo.getTipoDescarte().getId());
            if(quantidadeABaixar > 0 ) {
               int quantidadeSaldoDisponivel = combo.getSaldo();
               if(quantidadeSaldoDisponivel >= quantidadeABaixar) {
                   combo.setSaldo(combo.getSaldo() - quantidadeABaixar);
                   DescartePorCombo toSave = DescartePorCombo.builder()
                           .combo(combo)
                           .ctr(ctr)
                           .quantidade(quantidadeABaixar)
                           .dataDescarte(LocalDateTime.now())
                           .build();
                   descartePorCombos.add(toSave);
                   quantidadeNecessaria.remove(combo.getTipoDescarte().getId());
                   quantidadeNecessaria.put(combo.getTipoDescarte().getId(), combo.getSaldo() - quantidadeABaixar);
               } else {
                   DescartePorCombo toSave = DescartePorCombo.builder()
                           .combo(combo)
                           .ctr(ctr)
                           .quantidade(combo.getSaldo())
                           .dataDescarte(LocalDateTime.now())
                           .build();
                   descartePorCombos.add(toSave);
                   quantidadeNecessaria.remove(combo.getTipoDescarte().getId());
                   quantidadeNecessaria.put(combo.getTipoDescarte().getId(), quantidadeABaixar - combo.getSaldo());
                   combo.setSaldo(0);
               }
            }
        }
        descartePorComboRepository.saveAll(descartePorCombos);
    }

    @Override
     public void validate(CTR ctr) throws DomainException {
        if(Objects.isNull(ctr.getNumero()))
            throw new DomainException("Número Inválido!");
        if(Objects.isNull(ctr.getGerador())){
            throw new DomainException("Gerador Inválido!");
        }
        if(Objects.isNull(ctr.getTipoDescartes()) || ctr.getTipoDescartes().isEmpty()){
            throw new DomainException("Tipo de Descarte não informado!");
        }
        if(Objects.isNull(ctr.getVeiculo())){
            throw new DomainException("Veículo Inválido!");
        }
        if(Objects.isNull(ctr.getDestinatario())){
            throw new DomainException("Destinatário Inválido!");
        }
        if(Objects.isNull(ctr.getTransportador())){
            throw new DomainException("Transportador Inválido!");
        }
        if(ctr.getPagamentos().isEmpty()){
            throw new DomainException("Necessita informar pagamento(s)");
        }
        for (Pagamento pagamento : ctr.getPagamentos()) {
            if(!isDescarteSomenteCombo(ctr) && (Objects.isNull(pagamento.getValor()) || pagamento.getValor() < 0.01 ) )
                throw new DomainException("Valor de pagamento inválido!");
            if(Objects.isNull(pagamento.getFormaPagamento()))
                throw new DomainException("Forma de Pagamento inválido!");
            if(Objects.isNull(pagamento.getDataPagamento()))
                throw new DomainException("Data de Pagamento inválido!");
            if(Objects.isNull(pagamento.getInstituicaoBancaria()))
                throw new DomainException("Instituição bancária inválido!");
        }
        validaSePossuiPagamentoComboEOutro(ctr);
        checaSePossuiSaldoSuficiente(ctr);
    }

    private void validaSePossuiPagamentoComboEOutro(CTR ctr) throws DomainException {
        for (Pagamento pagamento : ctr.getPagamentos()) {
            if(pagamento.getFormaPagamento().getNome().contains("Combo") && ctr.getPagamentos().size() > 1){
                throw new DomainException("Se o CTR possuir pagamento via Combo, não pode existir outra forma de pagamento no mesmo CTR!");
            }
        }
    }

    @Override
    public CTR findById(String id) throws DomainException {
       return repository.findById(id).orElseThrow(() -> new DomainException("CTR com Id não encontrado!"));
    }

    private boolean isDescarteSomenteCombo(CTR ctr){
        return ctr.getPagamentos().size() == 1 && ctr.getPagamentos().get(0).getFormaPagamento().getNome().contains("Combo");
    }

    private void checaSePossuiSaldoSuficiente(CTR ctr) throws DomainException {
        List<String> idsTipoDescarte = retornaListaDeIdsDosTiposDescarteParaBaixa(ctr);

        Map<String, Integer> quantidadeDescartePorTipo = retornaQuantidadeNecessariaDeCombosPorTipoDeDescarte(idsTipoDescarte);

        for (Map.Entry<String, Integer> id : quantidadeDescartePorTipo.entrySet()){
            int quantidadeABaixar = quantidadeDescartePorTipo.get(id);
            int saldoDisponivel = comboService.retornaQuantidadeDeComboPorCategoria(ctr.getTransportador().getId(), id.getKey() );
            if(quantidadeABaixar > saldoDisponivel)
                throw new DomainException(String.format("Quantidade de descarte '%s' excede a quantidade de combo disponível!", ctr.getTipoDescartes().stream().filter(tipoDescarte -> tipoDescarte.getId() == id.getKey()).findFirst().get().getNome()));
        }
    }

    private List<String> retornaListaDeIdsDosTiposDescarteParaBaixa(CTR ctr) {
        List<String> idsTipoDescarte = new ArrayList<>();
        for (TipoDescarte tipoDescarte : ctr.getTipoDescartes()) {
            idsTipoDescarte.add(tipoDescarte.getId());
        }
        return idsTipoDescarte;
    }

    private Map<String, Integer> retornaQuantidadeNecessariaDeCombosPorTipoDeDescarte(List<String> idsTipoDescarte){
        Map<String, Integer> quantidadeDescartePorTipo = new HashMap<>();
        for (String id : idsTipoDescarte) {
            if(quantidadeDescartePorTipo.containsKey(id)){
                int acumulador = quantidadeDescartePorTipo.get(id);
                quantidadeDescartePorTipo.remove(id);
                quantidadeDescartePorTipo.put(id, ++acumulador);
            } else {
                quantidadeDescartePorTipo.put(id, 1);
            }
        }
        return quantidadeDescartePorTipo;
    }

    @Transactional
    public Page<CTR> findAll(Pageable pageable, String transportadorId, String numero, String dataDe, String dataAte) {
        CriteriaBuilder criteriaBuilder =  getEm().getCriteriaBuilder();
        CriteriaQuery<CTR> query = criteriaBuilder.createQuery(CTR.class);
        Root<CTR> root = query.from(CTR.class);
        query.select(root);
        getEm().createQuery(query);

        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(transportadorId) && !transportadorId.equals("null")){
            Predicate predicateForTransportador = criteriaBuilder.equal(root.get("transportador").get("id"), Long.parseLong(transportadorId));
            predicates.add(predicateForTransportador);
        }
        if(Objects.nonNull(numero) && !numero.equals("null")){
            Predicate predicateForId = criteriaBuilder.equal(root.get("id"), Long.parseLong(numero));
            predicates.add(predicateForId);
        }
        if(Objects.nonNull(dataDe) && !dataDe.equals("null")){
            LocalDateTime data = convertStringToDate(dataDe);
            Predicate predicateForDataDe = criteriaBuilder.greaterThanOrEqualTo(root.get("geracao"), data);
            predicates.add(predicateForDataDe);
        }
        if(Objects.nonNull(dataAte) && !dataAte.equals("null")){
            LocalDateTime data = convertStringToDate(dataAte);
            Predicate predicateForDataAte = criteriaBuilder.lessThanOrEqualTo(root.get("geracao"), data);
            predicates.add(predicateForDataAte);
        }
        if(!predicates.isEmpty())
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        query.orderBy(criteriaBuilder.desc(root.get("geracao")));

        List<CTR> resultList = getEm().createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<CTR> responseList = new ArrayList<>();
        for (CTR ctr : resultList) {
            responseList.add(CTR.builder()
                    .numero(ctr.getNumero())
                            .geracao(ctr.getGeracao())
                            .transportador(Transportador.builder().nome(ctr.getTransportador().getNome()).build())
                            .pagamentos(ctr.getPagamentos())
                    .build());
        }

        return new PageImpl<>(responseList, pageable, count(predicates));
    }

    private Long count(List<Predicate> predicates) {
        CriteriaQuery<Long> countQuery = getCriteriaBuilder().createQuery(Long.class);
        Root<CTR> root = countQuery.from(CTR.class);
        countQuery
                .select(getCriteriaBuilder().count(root))
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[predicates.size()])));

        return getEm().createQuery(countQuery).getSingleResult();
    }

    private CriteriaBuilder getCriteriaBuilder(){
        return getEm().getCriteriaBuilder();
    }

    private LocalDateTime convertStringToDate(String data) {
        if(data.length() <= 19)
            data = data.concat(" 00:00:00");
        return LocalDateTime.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
