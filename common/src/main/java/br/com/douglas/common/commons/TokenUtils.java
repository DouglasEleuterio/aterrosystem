package br.com.douglas.common.commons;

import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TokenUtils {

    private final ApplicationContext context;

    public TokenUtils(ApplicationContext context) {
        this.context = context;
    }

    public PasswordEncoder getPasswordEncoder() {
        return context.getBean(BCryptPasswordEncoder.class);
    }
}
