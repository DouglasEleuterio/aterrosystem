package br.com.douglas.service.interfaces.mail;

import br.com.douglas.entity.model.EmailEvent;
import br.com.douglas.exception.exceptions.DomainException;

public interface IMailService {

    void sendMail(EmailEvent emailEvent) throws DomainException;


}
