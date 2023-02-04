package br.com.douglas.service.impls.user;

import br.com.douglas.entity.entities.Token;
import br.com.douglas.exception.exceptions.user.ChangePasswordException;
import br.com.douglas.message.messages.Message;
import br.com.douglas.repository.repositories.TokenRepository;
import br.com.douglas.service.impls.BaseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService extends BaseService<Token> {

    private final TokenRepository tokenRepository;
    public TokenService(TokenRepository tokenRepository, TokenRepository tokenRepository1) {
        super(tokenRepository);
        this.tokenRepository = tokenRepository1;
    }

     public static String getToken(){
        return UUID.randomUUID().toString();
    }

    public Token findByToken(String token) throws ChangePasswordException {
        return tokenRepository.findByKeyEquals(token)
                .orElseThrow(() -> new ChangePasswordException(Message.toLocale("change_password.token.notfound")));
    }
}
