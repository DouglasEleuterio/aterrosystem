package br.com.douglas.service.exemplo;

import br.com.douglas.entity.entities.Veiculo;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.message.messages.Message;
import br.com.douglas.repository.repositories.VeiculoRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VeiculoService extends BaseService<Veiculo> implements IVeiculoService {

    public VeiculoService(VeiculoRepository veiculoRepo) {
        super(veiculoRepo);
    }

    @Override
    public void validate(Veiculo entity) throws DomainException {
        super.validate(entity);
        if(Objects.isNull(entity.getModelo()))
            throw new DomainException(Message.toLocale("veiculo.modelo-null"));
        if(Objects.isNull(entity.getDataFabricacao()))
            throw new DomainException(Message.toLocale("veiculo.dataFabricacao-null"));
        if(Objects.isNull(entity.getModelo().getCategoria()))
            throw new DomainException(Message.toLocale("veiculo.categoria-null"));
        if(Objects.isNull(entity.getDataLancamento()))
            throw new DomainException(Message.toLocale("veiculo.dataLancamento-null"));
    }
}
