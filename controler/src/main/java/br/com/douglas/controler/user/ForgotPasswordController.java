package br.com.douglas.controler.user;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.exception.exceptions.user.ChangePasswordException;
import br.com.douglas.service.interfaces.user.IChangePasswordService;
import br.com.douglas.service.interfaces.user.IRecoverPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.douglas.controler.mapper.mappers.user.ChangePasswordWithTokenRequest;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/recover")
public class ForgotPasswordController {

    private final IRecoverPasswordService recoverPasswordService;
    private final IChangePasswordService changePasswordService;

    public ForgotPasswordController(IRecoverPasswordService recoverPasswordService, IChangePasswordService changePasswordService) {
        this.recoverPasswordService = recoverPasswordService;
        this.changePasswordService = changePasswordService;
    }

    @PostMapping(path = "/{userName}")
    public ResponseEntity<Void> recoverPassword(@PathVariable("userName") String userName, @RequestParam("url-redirect") String parametro ) throws DomainException {
        recoverPasswordService.recoverPassword(userName);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .location(URI.create(parametro)).build();
    }

    @PostMapping
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordWithTokenRequest request) throws ChangePasswordException, DomainException {
        changePasswordService.changePasswordWithToken(request.getToken(), request.getUsername(), request.getNewPassword());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
