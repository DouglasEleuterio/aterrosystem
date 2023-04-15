package br.com.douglas.service.transportador;

import br.com.douglas.mapper.transportador.TransportadorFromSelect;
import br.com.douglas.mapper.veiculo.VeiculoFromSelect;
import br.com.douglas.repositories.transportador.TransportadorRepository;
import br.com.douglas.service.endereco.EnderecoService;
import br.com.douglas.entity.entities.temp.Transportador;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.service.impls.BaseService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransportadorService extends BaseService<Transportador> {

    private final TransportadorRepository repository;
    private final EnderecoService enderecoService;

    public TransportadorService(TransportadorRepository repository, EnderecoService enderecoService) {
        super(repository);
        this.repository = repository;
        this.enderecoService = enderecoService;
    }
    public Transportador save(Transportador transportador) {
        transportador.setAtivo(true);
        return repository.save(transportador);
    }

    @Override
    public void validate(Transportador transportador) throws DomainException{
        if(Objects.isNull(transportador.getNome()) || transportador.getNome().length() < 3){
            throw new DomainException("Nome inválido");
        }
        if(Objects.isNull(transportador.getRazaoSocial()) || transportador.getRazaoSocial().length() < 3){
            throw new DomainException("Razão Social Inválida");
        }
        if(Objects.isNull(transportador.getCnpj()) || transportador.getCnpj().length() < 14){
            throw new DomainException("CNPJ Inválido");
        }
        enderecoService.validate(transportador.getEndereco());
    }


    @Override
    public void delete(String id) throws DomainException{
        Optional<Transportador> optTipoDescarte = repository.findById(id);
        if(optTipoDescarte.isPresent()){
            Transportador entity = optTipoDescarte.get();
            repository.delete(entity);
        }else {
            throw new DomainException(String.format("Tipo de descarte com id %s não encontrado", id));
        }
    }

    public List<TransportadorFromSelect> findAllFromSelect(Sort sort) {
        return repository.findAllFromSelect(sort);
    }
}
