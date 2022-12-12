package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Transportador;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.TransportadorRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Transportador save(Transportador transportador) throws DomainException {
        transportador.setAtivo(true);
        return super.save(transportador);
    }

    void validate(Transportador transportador) throws DomainException{
        if(Objects.isNull(transportador.getEndereco())){
            throw new DomainException("Endereço inválido");
        }
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

    public List<Transportador> findAll(Sort sort) {
        List<Transportador> all = repository.findAll();
        all.forEach(transportador -> transportador.getVeiculos().forEach(veiculo -> veiculo.getTransportador().setVeiculos(new ArrayList<>())));
        return all;
    }

    public void delete(Long id) throws DomainException{
        Optional<Transportador> optTipoDescarte = repository.findById(id);
        if(optTipoDescarte.isPresent()){
            Transportador entity = optTipoDescarte.get();
            repository.delete(entity);
        }else {
            throw new DomainException(String.format("Tipo de descarte com id %s não encontrado", id));
        }
    }
}
