package io.github.fatihbozik.shoppingcart.fault;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorsView {
    private final String code;
    private final String message;
}
