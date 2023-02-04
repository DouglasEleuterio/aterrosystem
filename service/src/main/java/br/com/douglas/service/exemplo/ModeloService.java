package br.com.douglas.service.exemplo;

import br.com.douglas.entity.entities.Modelo;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.message.messages.Message;
import br.com.douglas.repository.BaseRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ModeloService extends BaseService<Modelo> {
    protected ModeloService(BaseRepository<Modelo> repository) {
        super(repository);
    }

    @Override
    public void validate(Modelo entity) throws DomainException {
        super.validate(entity);
        if(Objects.isNull(entity.getNome()) || entity.getNome().isEmpty() || entity.getNome().isBlank())
            throw new DomainException(Message.toLocale("modelo.nome-null"));
        if(Objects.isNull(entity.getMarca()))
            throw new DomainException(Message.toLocale("modelo.marca-null"));
    }
}
