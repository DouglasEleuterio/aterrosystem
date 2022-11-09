package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Transportador;
import br.com.douglas.aterrosystem.entity.Veiculo;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.TransportadorRepository;
import br.com.douglas.aterrosystem.repository.VeiculoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<Veiculo> findAll(Sort sort) {
//        List<Veiculo> all = repository.findAll();
//        all.forEach(transportador -> transportador.getVeiculos().forEach(veiculo -> veiculo.getTransportador().setVeiculos(new ArrayList<>())));
//        return all;
        return repository.findAll();
    }
}
