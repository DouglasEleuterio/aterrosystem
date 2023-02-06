package br.com.douglas.exception.handler;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.exception.exceptions.user.ChangePasswordException;
import br.com.douglas.exception.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERRO_NAO_ESPERADO = "Erro não esperado";
    private static final String ERRO_DE_TROCA_SENHA = "Erro no processo de alteração de senha";

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleDomainException(Exception ex, WebRequest request) {
        log.info(ERRO_NAO_ESPERADO, ex);
        return new ResponseEntity<>(ErrorResponse.badRequest(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(Exception ex, WebRequest request) {
        log.info(ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.badRequest(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIntegratyConstraintViolationException(Exception ex, WebRequest request) {
        log.info(ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.constraintException(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolationExceptionException(ConstraintViolationException ex, WebRequest request) {
        log.info(ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.constraintViolationException(ex, ex.getConstraintViolations().stream().toList().get(0).getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChangePasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleChangePasswordException(Exception ex, WebRequest request) {
        log.info(ERRO_DE_TROCA_SENHA, ex);
        return new ResponseEntity<>(ErrorResponse.badRequest(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
