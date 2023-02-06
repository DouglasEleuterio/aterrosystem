package br.com.douglas.service.impls.mail;

import br.com.douglas.service.interfaces.mail.IEmailHtmlParserService;
import br.com.douglas.entity.enums.Templates;
import br.com.douglas.entity.model.EmailEvent;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.message.messages.Message;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;

@Service
@Slf4j
public class EmailHtmlParserService implements IEmailHtmlParserService {

    private final Configuration configuration;

    public EmailHtmlParserService(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String parse(EmailEvent emailEvent) throws DomainException {
        try {
            var stringWriter = new StringWriter();
            configuration.getTemplate(getTemplateLocation(emailEvent.template())).process(emailEvent.params(), stringWriter);
            return stringWriter.getBuffer().toString();
        } catch (IOException e) {
            log.info("Erro no método de 'getTemplate()' na leitura do template {}", emailEvent.template().name());
            log.error(e.getMessage());
            throw new DomainException(e.getMessage(), e.getCause());
        }catch (TemplateException e){
            log.info("Erro no método 'process' ao realizar tentativa de processar os parametros do email");
            log.error(e.getMessage());
            throw new DomainException(e.getMessage(), e.getCause());
        }
    }

    private String getTemplateLocation(Templates template) throws DomainException {
        return switch (template) {
            case RECOVER_PASSWORD -> "recover-password.ftlh";
            default -> throw new DomainException(Message.toLocale("email.template-not-found", template.name()));
        };
    }
}
