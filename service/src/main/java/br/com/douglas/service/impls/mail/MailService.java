package br.com.douglas.service.impls.mail;

import br.com.douglas.entity.model.EmailEvent;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.service.interfaces.mail.IEmailHtmlParserService;
import br.com.douglas.service.interfaces.mail.IMailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailService implements IMailService {

    private final IEmailHtmlParserService emailHtmlParserService;
    private final JavaMailSender mailSender;

    public MailService(final IEmailHtmlParserService emailHtmlParserService,
                       final JavaMailSender mailSender) {
        this.emailHtmlParserService = emailHtmlParserService;
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(EmailEvent emailEvent) throws DomainException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(emailEvent.subject());
            helper.setTo(emailEvent.to());
            helper.setFrom(emailEvent.from());
            var emailContent = emailHtmlParserService.parse(emailEvent);
            helper.setText(emailContent, true);
            mailSender.send(mimeMessage);
        }catch (DomainException e){
            throw e;
        }catch (Exception e){
            throw new DomainException("Falha ao enviar email", e);
        }
    }
}
