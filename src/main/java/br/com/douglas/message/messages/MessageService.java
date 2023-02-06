package br.com.douglas.message.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageService {

    @Autowired
    private ResourceBundleMessageSource messageSource;
    private final Locale locale;

    public MessageService() {
        this.locale = getLocale();
        Message.setMessageService(this);
    }

    public String toLocale(String msgCode) {
        return messageSource.getMessage(msgCode, null, locale);
    }

    public String toLocale(String msgCode, Object... args) {
        return messageSource.getMessage(msgCode, args, locale);
    }

    public String toLocale(String msgCode, Locale locale, Object... args ) {
        return messageSource.getMessage(msgCode, args, locale);
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
