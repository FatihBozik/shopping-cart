package io.github.fatihbozik.shoppingcart.fault;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String errorCode;
    private final int httpStatus;

    public ServiceRuntimeException(String errorCode) {
        super("Error: " + errorCode);
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.BAD_REQUEST.value();
    }
}
