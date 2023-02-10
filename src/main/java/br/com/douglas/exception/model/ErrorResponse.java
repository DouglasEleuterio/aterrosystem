package br.com.douglas.exception.model;

import br.com.douglas.exception.exceptions.ValidationBagException;
import br.com.douglas.message.messages.Message;
import lombok.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private boolean validation;
    private String exception;
    private String stackTrace;
    private List<ValidationResponse> erros;

    public ErrorResponse(int status, String message, List<ValidationResponse> erros) {
        this.status = status;
        this.message = message;
        this.erros = erros;
    }

    public static ErrorResponse notMappedException(Exception ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(Message.toLocale("error.exception"))
                .exception(ex.getMessage())
                .stackTrace(ExceptionUtils.getStackTrace(ex))
                .build();
    }

    public static ErrorResponse badRequest(String message) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();
    }

    public static ErrorResponse withoutAuthorization() {
        return ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message(Message.toLocale("error.forbiden"))
                .build();
    }

    public static ErrorResponse withoutPermission() {
        return ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(Message.toLocale("error.unauthorized"))
                .build();
    }

    public static ErrorResponse constraintException(Exception ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getCause().getCause().getMessage())
                .exception(ex.getMessage())
                .stackTrace(ExceptionUtils.getStackTrace(ex))
                .build();
    }
    public static ErrorResponse constraintViolationException(Exception ex, String message) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .exception(ex.getMessage())
                .build();
    }

    public static ErrorResponse entityDataException(Exception ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Message.toLocale("error.entityData"))
                .exception(ex.getMessage())
                .stackTrace(ExceptionUtils.getStackTrace(ex))
                .build();
    }

    public static ErrorResponse validationBag(ValidationBagException ex) {
        return ErrorResponse.builder().build();
    }
    public static ErrorResponse a() {
        return ErrorResponse.builder().build();
    }
}
