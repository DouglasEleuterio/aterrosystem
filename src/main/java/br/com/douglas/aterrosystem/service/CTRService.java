package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.*;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.CTRRepository;
import br.com.douglas.aterrosystem.repository.DescartePorComboRepository;
import br.com.douglas.aterrosystem.repository.PagamentoRepository;
import br.com.douglas.aterrosystem.repository.TipoDescarteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CTRService extends BaseService<CTR> {

    private final CTRRepository repository;
    private final PagamentoRepository pagamentoRepository;
    private final TipoDescarteRepository tipoDescarteRepository;
    private final ComboService comboService;
    private final DescartePorComboRepository descartePorComboRepository;

    public CTRService(CTRRepository repository, PagamentoRepository pagamentoRepository, TipoDescarteRepository tipoDescarteRepository, ComboService comboService, DescartePorComboRepository descartePorComboRepository) {
        super(repository);
        this.repository = repository;
        this.pagamentoRepository = pagamentoRepository;
        this.tipoDescarteRepository = tipoDescarteRepository;
        this.comboService = comboService;
        this.descartePorComboRepository = descartePorComboRepository;
    }

    @Transactional
    public CTR save(CTR ctr) throws DomainException {
        validate(ctr);
        ctr.setAtivo(true);
        ctr.setGeracao(LocalDateTime.now());
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
        List<Long> ids = retornaListaDeIdsDosTiposDescarteParaBaixa(ctr);
        Map<Long, Integer> quantidadeNecessaria = retornaQuantidadeNecessariaDeCombosPorTipoDeDescarte(ids);
        List<Combo> combosABaixar = new ArrayList<>();
        Integer quantidadeABaixar = 0 ;
        for(Long id : quantidadeNecessaria.keySet()){
            combosABaixar.addAll(comboService.retornaComboParaConsumirSaldoNoDescarte(quantidadeNecessaria.get(id), ctr.getTransportador().getId(), id));
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

     void validate(CTR ctr) throws DomainException {
        if(Objects.isNull(ctr.getId()))
            throw new DomainException("Número Inválido");
        if(Objects.isNull(ctr.getGerador())){
            throw new DomainException("Gerador Inválido");
        }
        if(Objects.isNull(ctr.getTipoDescartes()) || ctr.getTipoDescartes().isEmpty()){
            throw new DomainException("Tipo de Descarte não informado");
        }
        if(Objects.isNull(ctr.getVeiculo())){
            throw new DomainException("Veículo Inválido");
        }
        if(Objects.isNull(ctr.getDestinatario())){
            throw new DomainException("Destinatário Inválido");
        }
        if(Objects.isNull(ctr.getTransportador())){
            throw new DomainException("Transportador Inválido");
        }
        if(ctr.getPagamentos().isEmpty()){
            throw new DomainException("Necessita informar pagamento(s)");
        }
        for (Pagamento pagamento : ctr.getPagamentos()) {
            if(!isDescarteSomenteCombo(ctr) && (Objects.isNull(pagamento.getValor()) || pagamento.getValor() < 0.01 ) )
                throw new DomainException("Valor de pagamento inválido");
            if(Objects.isNull(pagamento.getFormaPagamento()))
                throw new DomainException("Forma de Pagamento inválido");
            if(Objects.isNull(pagamento.getDataPagamento()))
                throw new DomainException("Data de Pagamento inválido");
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

    public CTR findById(String id) throws DomainException {
        CTR ctr = repository.findById(Long.parseLong(id)).orElseThrow(() -> new DomainException("CTR com Id não encontrado!"));
        return ctr;
    }

    private boolean isDescarteSomenteCombo(CTR ctr){
        if(ctr.getPagamentos().size() == 1 && ctr.getPagamentos().get(0).getFormaPagamento().getNome().contains("Combo"))
            return true;
        return false;
    }

    private void checaSePossuiSaldoSuficiente(CTR ctr) throws DomainException {
        List<Long> idsTipoDescarte = retornaListaDeIdsDosTiposDescarteParaBaixa(ctr);

        Map<Long, Integer> quantidadeDescartePorTipo = retornaQuantidadeNecessariaDeCombosPorTipoDeDescarte(idsTipoDescarte);

        for (Long id : quantidadeDescartePorTipo.keySet()){
            int quantidadeABaixar = quantidadeDescartePorTipo.get(id);
            int saldoDisponivel = comboService.retornaQuantidadeDeComboPorCategoria(ctr.getTransportador().getId(), id );
            if(quantidadeABaixar > saldoDisponivel)
                throw new DomainException(String.format("Quantidade de descarte '%s' excede a quantidade de combo disponível!", ctr.getTipoDescartes().stream().filter(tipoDescarte -> tipoDescarte.getId() == Integer.parseInt(id.toString())).findFirst().get().getNome()));
        }
    }

    private List<Long> retornaListaDeIdsDosTiposDescarteParaBaixa(CTR ctr) {
        List<Long> idsTipoDescarte = new ArrayList<>();
        for (TipoDescarte tipoDescarte : ctr.getTipoDescartes()) {
            idsTipoDescarte.add(tipoDescarte.getId());
        }
        return idsTipoDescarte;
    }

    private Map<Long, Integer> retornaQuantidadeNecessariaDeCombosPorTipoDeDescarte(List<Long> idsTipoDescarte){
        Map<Long, Integer> quantidadeDescartePorTipo = new HashMap<>();
        for (Long id : idsTipoDescarte) {
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

        return new PageImpl<>(resultList, pageable, count(predicates));
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
