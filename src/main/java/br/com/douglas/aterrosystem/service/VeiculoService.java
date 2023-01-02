package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Veiculo;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.TransportadorRepository;
import br.com.douglas.aterrosystem.repository.VeiculoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;
    private final TransportadorRepository transportadorRepository;

    public VeiculoService(VeiculoRepository repository, TransportadorRepository transportadorRepository) {
        this.repository = repository;
        this.transportadorRepository = transportadorRepository;
    }

    @Transactional
    public Veiculo save(Veiculo veiculo) throws DomainException {
        validate(veiculo);
        veiculo.setAtivo(true);
        veiculo.setTransportador(transportadorRepository.findById(veiculo.getTransportador().getId()).get());
        return repository.save(veiculo);
    }

    private void validate(Veiculo veiculo) throws DomainException{
//        if(Objects.isNull(veiculo.getTransportador())){
//            throw new DomainException("Transportador inválido");
//        }
        if(Objects.isNull(veiculo.getMarca()) || veiculo.getMarca().length() < 2){
            throw new DomainException("Marca inválida");
        }
        if(Objects.isNull(veiculo.getModelo()) || veiculo.getModelo().length() < 1){
            throw new DomainException("Modelo Inválido");
        }
        if(Objects.isNull(veiculo.getPlaca()) || veiculo.getPlaca().length() < 7){
            throw new DomainException("Placa inválida");
        }
    }

    public Page<Veiculo> findAll(Pageable page, String placa, String modelo, String ativoP) {
        Boolean ativo = Boolean.valueOf(ativoP);
        if( placa.equals("null") || placa.equals("") )
            placa = null;
        if( modelo.equals("null") || modelo.equals("") )
            modelo = null;
        if( ativoP.equals("null") || ativoP.equals("") )
            ativo = true;

        if(Objects.isNull(placa) && Objects.isNull(modelo))
            return repository.findAllByAtivo(page, ativo);
        else if(Objects.nonNull(placa) && Objects.nonNull(modelo))
            return repository.findAllByPlacaAndModelo(page, placa, modelo, ativo);
        else if(Objects.nonNull(placa))
            return repository.findAllByPlaca(page, placa, ativo);
        else if(Objects.nonNull(modelo))
            return repository.findAllByModelo(page, modelo, ativo);
        return null;
    }

    public void delete(Long id) throws DomainException{
        Optional<Veiculo> optTipoDescarte = repository.findById(id);
        if(optTipoDescarte.isPresent()){
            Veiculo entity = optTipoDescarte.get();
            entity.setAtivo(false);
            repository.save(entity);
        }else {
            throw new DomainException(String.format("Veículo com id %s não encontrado", id));
        }
    }
}
