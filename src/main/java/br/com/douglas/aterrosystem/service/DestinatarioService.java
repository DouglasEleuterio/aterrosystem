package br.com.douglas.aterrosystem.service;

import br.com.douglas.aterrosystem.entity.Destinatario;
import br.com.douglas.aterrosystem.entity.Gerador;
import br.com.douglas.aterrosystem.exception.DomainException;
import br.com.douglas.aterrosystem.repository.DestinatarioRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DestinatarioService {

    private final DestinatarioRepository repository;

    public DestinatarioService(DestinatarioRepository repository) {
        this.repository = repository;
    }

    public Destinatario save(Destinatario destinatario) throws DomainException {
        validate(destinatario);
        return repository.save(destinatario);
    }

    private void validate(Destinatario destinatario) throws DomainException {
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
}
