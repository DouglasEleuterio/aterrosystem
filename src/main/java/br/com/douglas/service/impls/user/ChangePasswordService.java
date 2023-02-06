package br.com.douglas.service.impls.user;

import br.com.douglas.service.user.UserService;
import br.com.douglas.shared.TokenUtils;
import br.com.douglas.entity.entities.Token;
import br.com.douglas.entity.entities.User;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.exception.exceptions.user.ChangePasswordException;
import br.com.douglas.message.messages.Message;
import br.com.douglas.service.interfaces.user.IChangePasswordService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ChangePasswordService implements IChangePasswordService {

    private final UserService userService;
    private final TokenService tokenService;
    private final TokenUtils tokenUtils;


    public ChangePasswordService(UserService userService, TokenService tokenService, TokenUtils tokenUtils) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.tokenUtils = tokenUtils;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePasswordWithToken(@NonNull String token, @NonNull String username, @NonNull String newPassword) throws ChangePasswordException, DomainException {
        var tokenEntity = tokenService.findByToken(token);
        validarToken(tokenEntity, tokenEntity.getUser(), newPassword);
        var passwordEncoded = tokenUtils.getPasswordEncoder().encode(newPassword);
        tokenEntity.getUser().setPassword(passwordEncoded);
    }

    private void validarToken(@NonNull Token token, @NonNull User user, @NonNull String newPassword) throws ChangePasswordException, DomainException {
        if(token.getExpiration().isBefore(LocalDateTime.now()))
            throw new ChangePasswordException(Message.toLocale("token.expired"));
        if(!token.getUser().getUsername().equals(user.getUsername()))
            throw new ChangePasswordException(Message.toLocale("change_password.token.notfound"));
        userService.validarSeSenhaForte(newPassword);
    }

    @Override
    public void changePasswordWithUsernameAndPassword(@NonNull String username, @NonNull String password, @NonNull String newPassword) throws ChangePasswordException {
        throw new UnsupportedOperationException("Implementar");
    }
}
