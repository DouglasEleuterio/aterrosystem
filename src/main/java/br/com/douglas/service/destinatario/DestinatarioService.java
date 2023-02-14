package br.com.douglas.service.destinatario;

import br.com.douglas.entity.entities.temp.Destinatario;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.destinatario.DestinatarioMapper;
import br.com.douglas.mapper.destinatario.DestinatarioResponse;
import br.com.douglas.repositories.destinatario.DestinatarioRepository;
import br.com.douglas.service.impls.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DestinatarioService extends BaseService<Destinatario> {

    private final DestinatarioRepository repository;

    public DestinatarioService(DestinatarioRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Destinatario save(Destinatario destinatario) throws DomainException {
        validate(destinatario);
        return repository.save(destinatario);
    }

    @Override
    public void validate(Destinatario destinatario) throws DomainException {
        if(Objects.isNull(destinatario.getEnderecoRecebimento())){
            throw new DomainException("Endereço de Recebimento Inválido");
        }
        if(Objects.isNull(destinatario.getNome()) || destinatario.getNome().length() < 2){
            throw new DomainException("Nome inválido");
        }
        if(Objects.isNull(destinatario.getRazaoSocial()) || destinatario.getRazaoSocial().length() < 1){
            throw new DomainException("Razão Social Inválida");
        }
        if(Objects.isNull(destinatario.getCnpj()) || destinatario.getCnpj().length() < 14){
            throw new DomainException("CNPJ inválido");
        }
    }

    @Transactional
    public List<DestinatarioResponse> findAllWithFunction(Sort sort, DestinatarioMapper mapper) {
        var all = repository.findAll(sort);
        return mapper.toResponseList(all);
    }
}
