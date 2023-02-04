package br.com.douglas.service.impls.user;

import br.com.douglas.entity.entities.Token;
import br.com.douglas.entity.entities.User;
import br.com.douglas.entity.enums.Templates;
import br.com.douglas.entity.model.EmailEvent;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.message.messages.Message;
import br.com.douglas.service.interfaces.mail.IMailService;
import br.com.douglas.service.interfaces.user.IRecoverPasswordService;
import br.com.douglas.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
public class RecoverPasswordService implements IRecoverPasswordService {

    private final UserService userService;
    private final TokenService tokenService;
    private final IMailService mailService;

    public RecoverPasswordService(UserService userService, TokenService tokenService, IMailService mailService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mailService = mailService;
    }

    @Override
    public void validateUsername(String username) throws DomainException {
        if (Objects.isNull(username) || Strings.isBlank(username) || Strings.isEmpty(username))
            throw new DomainException(Message.toLocale("user.username-not-found"));
    }

    @Override
    public User getUserByUsername(String username) throws DomainException {
        return userService.findByUsername(username);
    }

    @Override
    @Transactional
    public Token createToken(User user) throws DomainException {
        return tokenService.create(Token.builder()
                .expiration(LocalDateTime.now().plus(24, ChronoUnit.HOURS))
                .user(user)
                .key(TokenService.getToken())
                .build());
    }

    @Override
    @Async
    public void sendEmail(User user, Token token) throws DomainException {
        var params = new HashMap<String, Object>();
        params.put("token", token.getKey());
        params.put("user", user.getFirstname().concat(" ").concat(user.getLastname()));
        params.put("sistema", "AterroSystem");

        var emailEvent = new EmailEvent(
                "AterroSystem - Recuperação de Senha",
                user.getEmail(),
                "douglas.versato@gmail.com",
                Templates.RECOVER_PASSWORD,
                params);
        log.info("Enviando email....");
        mailService.sendMail(emailEvent);
        //Enviar email
        //Ao final do processo, direcionar via redirect usuário para página que informará o nome de usuário e token.
        //Ao submeter o formulário, se válido, direcionar usuário para página de criação de nova senha.
        //Ao atualizar a senha, invalidar token apagando-o da tabela.
        //todO anotações.
    }

    @Override
    public void invalidateToken(Token token) {
        throw new  UnsupportedOperationException("IMPLEMENTAR");
    }
}
