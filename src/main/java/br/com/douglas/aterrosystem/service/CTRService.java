package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.CTR;
import br.com.douglas.aterrosystem.entity.Pagamento;
import br.com.douglas.aterrosystem.entity.TipoDescarte;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.CTRRepository;
import br.com.douglas.aterrosystem.repository.PagamentoRepository;
import br.com.douglas.aterrosystem.repository.TipoDescarteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class CTRService {

    private final CTRRepository repository;
    private final PagamentoRepository pagamentoRepository;
    private final TipoDescarteRepository tipoDescarteRepository;

    public CTRService(CTRRepository repository, PagamentoRepository pagamentoRepository, TipoDescarteRepository tipoDescarteRepository) {
        this.repository = repository;
        this.pagamentoRepository = pagamentoRepository;
        this.tipoDescarteRepository = tipoDescarteRepository;
    }

    @Transactional
    public CTR save(CTR ctr) throws DomainException {
        validate(ctr);
        ctr.setGeracao(LocalDate.now());
        ctr.getPagamentos().forEach(pagamento -> pagamento.setCtr(ctr));
        List<Pagamento> pagamentos = pagamentoRepository.saveAll(ctr.getPagamentos());
        ctr.setPagamentos(pagamentos);
        ctr.setTipoDescartes(ctr.getTipoDescartes());
        return repository.save(ctr);
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
            if(pagamento.getValor() < 0.01)
                throw new DomainException("Valor de pagamento inválido");
            if(Objects.isNull(pagamento.getFormaPagamento()))
                throw new DomainException("Forma de Pagamento inválido");
            if(Objects.isNull(pagamento.getDataPagamento()))
                throw new DomainException("Data de Pagamento inválido");
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
}
