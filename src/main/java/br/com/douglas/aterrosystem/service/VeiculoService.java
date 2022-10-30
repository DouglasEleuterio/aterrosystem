package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Transportador;
import br.com.douglas.aterrosystem.entity.Veiculo;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;

    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    public Veiculo save(Veiculo veiculo) throws DomainException {
        validate(veiculo);
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
}
