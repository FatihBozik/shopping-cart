package io.github.fatihbozik.shoppingcart.fault;

import io.github.fatihbozik.shoppingcart.util.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomRestExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CustomRestExceptionHandler.class);

    @ExceptionHandler(value = {ServiceRuntimeException.class})
    public ResponseEntity<ErrorsView> onServiceRuntimeException(ServiceRuntimeException e) {
        LOG.info("onServiceRuntimeException::{}", e.getMessage(), e);
        final ErrorsView view = new ErrorsView(e.getErrorCode(), Errors.getDescription(e.getErrorCode()));
        return ResponseEntity.status(e.getHttpStatus()).body(view);
    }
}
