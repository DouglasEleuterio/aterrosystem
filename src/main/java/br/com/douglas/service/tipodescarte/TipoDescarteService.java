package br.com.douglas.service.tipodescarte;

import br.com.douglas.entity.entities.temp.TipoDescarte;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.repositories.tipodescarte.TipoDescarteRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoDescarteService extends BaseService<TipoDescarte> {

    private final TipoDescarteRepository repository;
    public TipoDescarteService(TipoDescarteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public TipoDescarte save(TipoDescarte entity) {
        if(!isEditing(entity))
            entity.setAtivo(true);
        return repository.save(entity);
    }

    private boolean isEditing(TipoDescarte entity) {
        return Objects.nonNull(entity.getId());
    }

    @Override
    public void validate(TipoDescarte entity) throws DomainException {
        if(Objects.isNull(entity.getValor()) || entity.getValor() < Double.valueOf("0.01"))
            throw new DomainException("Valor obrigatório");
    }

    public List<TipoDescarte> findAllAtivo(Sort sort){
        return repository.findAllAtivo( sort);
    }

    public TipoDescarte update(TipoDescarte entity) throws DomainException {
        Optional<TipoDescarte> optTipoDescarte = repository.findById(entity.getId());
        if (optTipoDescarte.isPresent()) {
            optTipoDescarte.get().setNome(entity.getNome());
            optTipoDescarte.get().setValor(entity.getValor());
            return repository.save(optTipoDescarte.get());
        }else {
            throw new DomainException(String.format("Tipo de descarte com id %s não encontrado", entity.getId()));
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(String id) throws DomainException{
        Optional<TipoDescarte> optTipoDescarte = repository.findById(id);
        if(optTipoDescarte.isPresent()){
            TipoDescarte entity = optTipoDescarte.get();
            entity.setAtivo(false);
        }else {
            throw new DomainException(String.format("Tipo de descarte com id %s não encontrado", id));
        }
    }

    public Page<TipoDescarte> findAll(Pageable pageable, String name, String ativo){
        String nomeNull = name.equals("null") ? null : name;
        if(Objects.isNull(nomeNull))
            return repository.findAllByAtivo(pageable, Boolean.valueOf(ativo));
        else
            return repository.findAllWithParams(pageable, nomeNull, Boolean.valueOf(ativo));
    }
}
