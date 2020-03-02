package io.github.fatihbozik.shoppingcart.fault;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static io.github.fatihbozik.shoppingcart.util.Errors.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
class CustomRestExceptionHandlerTest {
    @InjectMocks
    private CustomRestExceptionHandler customRestExceptionHandler;

    @Test
    void shouldReturnCorrectErrorMessage() {
        final ServiceRuntimeException serviceRuntimeException = new ServiceRuntimeException(SHOPPING_CART_NOT_FOUND);
        ResponseEntity<ErrorsView> responseEntity = customRestExceptionHandler.onServiceRuntimeException(serviceRuntimeException);

        assertThat(Objects.requireNonNull(responseEntity.getBody()).getCode(), is(SHOPPING_CART_NOT_FOUND));
        assertThat(responseEntity.getBody().getMessage(), is(SHOPPING_CART_NOT_FOUND_DESCRIPTION));
    }
}
