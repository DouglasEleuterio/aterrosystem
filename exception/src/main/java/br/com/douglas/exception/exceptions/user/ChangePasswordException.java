package br.com.douglas.exception.exceptions.user;

public class ChangePasswordException extends Exception {

    public ChangePasswordException(String message) {
        super(message);
    }

    public ChangePasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
