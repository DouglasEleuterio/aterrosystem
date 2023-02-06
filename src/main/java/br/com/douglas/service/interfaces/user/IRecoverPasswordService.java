package br.com.douglas.service.interfaces.user;

import br.com.douglas.entity.entities.Token;
import br.com.douglas.entity.entities.User;
import br.com.douglas.exception.exceptions.DomainException;

public interface IRecoverPasswordService {

    void validateUsername(String username) throws DomainException;

    User getUserByUsername(String username) throws DomainException;

    Token createToken(User user) throws DomainException;

    void sendEmail(User user, Token token) throws DomainException;

    void invalidateToken(Token token);

    default void recoverPassword(String username) throws DomainException {
        validateUsername(username);
        var user = getUserByUsername(username);
        var tokenEntity = createToken(user);
        sendEmail(user, tokenEntity);
    }
}
