package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Combo;
import br.com.douglas.aterrosystem.entity.TipoDescarte;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.BaseRepository;
import br.com.douglas.aterrosystem.repository.ComboRepository;
import br.com.douglas.aterrosystem.repository.TipoDescarteRepository;
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

    public TipoDescarteService(TipoDescarteRepository repository) {
        super(repository);
    }

    public TipoDescarte save(TipoDescarte entity) throws DomainException{
        if(!isEditing(entity))
            entity.setAtivo(true);
        return super.save(entity);
    }

    private boolean isEditing(TipoDescarte entity) {
        return Objects.nonNull(entity.getId());
    }

    void validate(TipoDescarte entity) throws DomainException {
        if(Objects.isNull(entity.getValor()) || entity.getValor() < Double.valueOf("0.01"))
            throw new DomainException("Valor obrigatório");
    }

    public List<TipoDescarte> findAllAtivo(Sort sort){
        return ((TipoDescarteRepository) getRepository()).findAllAtivo( sort);
    }

    public TipoDescarte update(TipoDescarte entity) throws DomainException {
        Optional<TipoDescarte> optTipoDescarte = getRepository().findById(entity.getId());
        if (optTipoDescarte.isPresent()) {
            optTipoDescarte.get().setNome(entity.getNome());
            optTipoDescarte.get().setValor(entity.getValor());
            return super.save(optTipoDescarte.get());
        }else {
            throw new DomainException(String.format("Tipo de descarte com id %s não encontrado", entity.getId()));
        }
    }

    @Transactional
    public void delete(Long id) throws DomainException{
        Optional<TipoDescarte> optTipoDescarte = getRepository().findById(id);
        if(optTipoDescarte.isPresent()){
            TipoDescarte entity = optTipoDescarte.get();
            entity.setAtivo(false);
        }else {
            throw new DomainException(String.format("Tipo de descarte com id %s não encontrado", id));
        }
    }

    public Page<TipoDescarte> findAll(Pageable pageable, String name, String ativo){
        if(Objects.isNull(name) && Objects.isNull(ativo))
            return getRepository().findAll(pageable);
        else
            return ((TipoDescarteRepository) getRepository()).findAllWithParams(pageable, name, Boolean.valueOf(ativo));
    }
}
