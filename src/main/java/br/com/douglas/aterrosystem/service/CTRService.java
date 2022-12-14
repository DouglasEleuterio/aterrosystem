package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.*;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.CTRRepository;
import br.com.douglas.aterrosystem.repository.DescartePorComboRepository;
import br.com.douglas.aterrosystem.repository.PagamentoRepository;
import br.com.douglas.aterrosystem.repository.TipoDescarteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class CTRService {

    private final CTRRepository repository;
    private final PagamentoRepository pagamentoRepository;
    private final TipoDescarteRepository tipoDescarteRepository;
    private final ComboService comboService;
    private final DescartePorComboRepository descartePorComboRepository;

    public CTRService(CTRRepository repository, PagamentoRepository pagamentoRepository, TipoDescarteRepository tipoDescarteRepository, ComboService comboService, DescartePorComboRepository descartePorComboRepository) {
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
        ctr.setGeracao(LocalDate.now());
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

    private void validate(CTR ctr) throws DomainException {
        if(Objects.isNull(ctr.getGerador())){
            throw new DomainException("Gerador Inválido");
        }
        if(Objects.isNull(ctr.getTipoDescartes()) || ctr.getTipoDescartes().isEmpty()){
            throw new DomainException("Tipo de Descarte não informado");
        }
        if(Objects.isNull(ctr.getVeiculo())){
            throw new DomainException("Gerador Inválido");
        }
        if(Objects.isNull(ctr.getDestinatario())){
            throw new DomainException("Gerador Inválido");
        }
        if(Objects.isNull(ctr.getTransportador())){
            throw new DomainException("Gerador Inválido");
        }
        if(ctr.getPagamentos().isEmpty()){
            throw new DomainException("Necessita informar pagamento(s)");
        }
        for (Pagamento pagamento : ctr.getPagamentos()) {
            if(!isDescarteSomenteCombo(ctr) && pagamento.getValor() < 0.01 )
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
        ctr.getVeiculo().getTransportador().setVeiculos(null);
        return ctr;
    }

    public Iterable<CTR> findAll(Sort sort) {
        List<CTR> all = repository.findAll(sort);
        all.forEach(ctr -> ctr.getVeiculo().getTransportador().setVeiculos(null));
        return all;
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
}
