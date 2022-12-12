package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Aquisicao;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.AquisicaoRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AquisicaoService extends BaseService<Aquisicao>{

    private final ComboService comboService;

    public AquisicaoService(AquisicaoRepository repository, ComboService comboService) {
        super(repository);
        this.comboService = comboService;
    }

    @Override
    public void validate(Aquisicao aquisicao) throws DomainException {
        if(Objects.isNull(aquisicao.getQuantidadeAdquirida()) || aquisicao.getQuantidadeAdquirida() < 1){
            throw new DomainException("Quantidade inválida");
        }
        if(Objects.isNull(aquisicao.getDataPagamento())){
            throw new DomainException("Data de pagamento inválida");
        }
        if(Objects.isNull(aquisicao.getValorPago()) || aquisicao.getValorPago() < Double.valueOf("0.01")){
            throw new DomainException("Valor pago inválido");
        }
        if(Objects.isNull(aquisicao.getFormaPagamento()) || aquisicao.getFormaPagamento().getId() == null){
            throw new DomainException("Forma de Pagamento inválido");
        }
    }

    @Override
    public Aquisicao save(Aquisicao aquisicao) throws DomainException {
        comboService.preparaCombo(aquisicao.getCombo());
        Double totalDoCombo =  getValorAdquirido(aquisicao);
        aquisicao.setAtivo(true);
        aquisicao.setDesconto(totalDoCombo - aquisicao.getValorPago());
        aquisicao.getCombo().setSaldo(aquisicao.getQuantidadeAdquirida());
        comboService.validate(aquisicao.getCombo());
        return super.save(aquisicao);
    }

    private Integer quantidadeAdquirida(Aquisicao aquisicao) {
        return  aquisicao.getQuantidadeAdquirida();
    }

    private Double getValorAdquirido(Aquisicao aquisicao) {
        return aquisicao.getCombo().getTipoDescarte().getValor() * quantidadeAdquirida(aquisicao);
    }
}
