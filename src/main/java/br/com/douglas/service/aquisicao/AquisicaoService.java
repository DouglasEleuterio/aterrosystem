package br.com.douglas.service.aquisicao;

import br.com.douglas.repositories.aquisicao.AquisicaoRepository;
import br.com.douglas.service.combo.ComboService;
import br.com.douglas.entity.entities.temp.Aquisicao;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;

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

    public Aquisicao save(Aquisicao aquisicao) throws DomainException {
        comboService.preparaCombo(aquisicao.getCombo());
        Double totalDoCombo =  getValorAdquirido(aquisicao);
        aquisicao.setAtivo(true);
        aquisicao.setDesconto(totalDoCombo - aquisicao.getValorPago());
        aquisicao.getCombo().setSaldo(aquisicao.getQuantidadeAdquirida());
        comboService.validate(aquisicao.getCombo());
        return repository.save(aquisicao);
    }

    private Integer quantidadeAdquirida(Aquisicao aquisicao) {
        return  aquisicao.getQuantidadeAdquirida();
    }

    private Double getValorAdquirido(Aquisicao aquisicao) {
        return aquisicao.getCombo().getTipoDescarte().getValor() * quantidadeAdquirida(aquisicao);
    }

    public Aquisicao findByComboId(String comboId) throws DomainException {
        Long idCombo = Long.parseLong(comboId);
        return repository.findByComboId(idCombo)
                .orElseThrow(() -> new DomainException(String.format("Aquisição do Combo id: {0} não encontrado!", comboId)));
    }

}
