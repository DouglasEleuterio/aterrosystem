package br.com.douglas.service.aquisicao;

import br.com.douglas.entity.entities.temp.Pagamento;
import br.com.douglas.repositories.aquisicao.AquisicaoRepository;
import br.com.douglas.service.combo.ComboService;
import br.com.douglas.entity.entities.temp.Aquisicao;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AquisicaoService extends BaseService<Aquisicao> {

    private final AquisicaoRepository repository;
    private final ComboService comboService;

    public AquisicaoService(AquisicaoRepository repository, AquisicaoRepository repository1, ComboService comboService) {
        super(repository);
        this.repository = repository1;
        this.comboService = comboService;
    }

    @Override
    public void validate(Aquisicao aquisicao) throws DomainException {
        if(Objects.isNull(aquisicao.getCombo().getTransportador()))
            throw new DomainException("Transportadora não informada");
        if(Objects.isNull(aquisicao.getCombo().getTipoDescarte()))
            throw new DomainException("Tipo de Descarte não informado");
        if(Objects.isNull(aquisicao.getFormaPagamento()))
            throw new DomainException("Forma de Pagamento não informado");
        if(Objects.isNull(aquisicao.getQuantidadeAdquirida()) || aquisicao.getQuantidadeAdquirida() < 1)
            throw new DomainException("Quantidade inválida");
        if(Objects.isNull(aquisicao.getDataPagamento()))
            throw new DomainException("Data de pagamento inválida");
        if(Objects.isNull(aquisicao.getCombo().getPagamentos()))
            throw new DomainException("Forma de Pagamento inválido");

        if(0 >= getValorTotal(aquisicao.getCombo().getPagamentos()))
            throw new DomainException("Valor pago inválido");
    }

    private Integer getValorTotal(List<Pagamento> pagamentos) {
        var valorTotal = 0;
        for (Pagamento pagamento : pagamentos)
            valorTotal += pagamento.getValor();
        return valorTotal;
    }

    @Override
    public Aquisicao create(Aquisicao entity) throws DomainException {
        validate(entity);
        return save(entity);
    }

    public Aquisicao save(Aquisicao aquisicao) throws DomainException {
        comboService.preparaCombo(aquisicao.getCombo());
        Double totalDoCombo =  getValorAdquirido(aquisicao);
        aquisicao.setAtivo(true);
        aquisicao.setDesconto(totalDoCombo - getValorTotal(aquisicao.getCombo().getPagamentos()));
        aquisicao.getCombo().setSaldo(aquisicao.getQuantidadeAdquirida());
        comboService.validate(aquisicao.getCombo());
        //Adicionar combo no pagamento
        aquisicao.getCombo().getPagamentos().forEach(pagamento -> pagamento.setCombo(aquisicao.getCombo()));
        return repository.save(aquisicao);
    }

    private Double getValorAdquirido(Aquisicao aquisicao) {
        return aquisicao.getCombo().getTipoDescarte().getValor() * aquisicao.getQuantidadeAdquirida();
    }

    public Aquisicao findByComboId(String comboId) throws DomainException {
        return repository.findByComboId(comboId)
                .orElseThrow(() -> new DomainException("Aquisição do Combo id: {0} não encontrado!".replace("{0}", comboId)));
    }

}
