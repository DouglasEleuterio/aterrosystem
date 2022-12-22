package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Veiculo;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.TransportadorRepository;
import br.com.douglas.aterrosystem.repository.VeiculoRepository;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public Page<Veiculo> findAll(Pageable page, String placa, String modelo) {
        Page<Veiculo> all = null;
        if(Objects.isNull(placa) && Objects.isNull(modelo))
            all = repository.findAll(page);
        else if(Objects.nonNull(placa) && Objects.nonNull(modelo))
            all = repository.findAllByPlacaAndModelo(page, placa, modelo);
        else if(Objects.isNull(placa) || Objects.isNull(modelo))
            all = repository.findAllByOneParam(page, placa, modelo);
        return all;
    }

    public void delete(Long id) throws DomainException{
        Optional<Veiculo> optTipoDescarte = repository.findById(id);
        if(optTipoDescarte.isPresent()){
            Veiculo entity = optTipoDescarte.get();
            repository.delete(entity);
        }else {
            throw new DomainException(String.format("Veículo com id %s não encontrado", id));
        }
    }
}
