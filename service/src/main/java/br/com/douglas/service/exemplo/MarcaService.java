package br.com.douglas.service.exemplo;

import br.com.douglas.entity.entities.Marca;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.message.messages.Message;
import br.com.douglas.repository.BaseRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MarcaService extends BaseService<Marca> {
    protected MarcaService(BaseRepository<Marca> repository) {
        super(repository);
    }

    @Override
    public void validate(Marca entity) throws DomainException {
        super.validate(entity);
        if(Objects.isNull(entity.getNome()))
            throw new DomainException(Message.toLocale("marca.nome-null"));
    }
}
