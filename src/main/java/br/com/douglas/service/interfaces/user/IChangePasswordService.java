package br.com.douglas.service.interfaces.user;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.exception.exceptions.user.ChangePasswordException;
import lombok.NonNull;

public interface IChangePasswordService {

    void changePasswordWithToken(@NonNull String token, @NonNull String username, @NonNull String newPassword) throws ChangePasswordException, DomainException;

    void changePasswordWithUsernameAndPassword(@NonNull String username, @NonNull String password, @NonNull String newPassword) throws ChangePasswordException;
}
