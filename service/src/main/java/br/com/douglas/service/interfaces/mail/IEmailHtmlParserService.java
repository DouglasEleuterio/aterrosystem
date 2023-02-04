package br.com.douglas.service.interfaces.mail;

import br.com.douglas.entity.model.EmailEvent;
import br.com.douglas.exception.exceptions.DomainException;

public interface IEmailHtmlParserService {

    String parse(final EmailEvent emailEvent) throws DomainException;
}
