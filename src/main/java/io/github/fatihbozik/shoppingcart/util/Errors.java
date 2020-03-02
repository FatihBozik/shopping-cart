package io.github.fatihbozik.shoppingcart.util;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Errors {
    public static final String SHOPPING_CART_NOT_FOUND = "SC-ERR-001";
    public static final String SHOPPING_CART_NOT_FOUND_DESCRIPTION = "Shopping Cart not found.";

    public static String getDescription(String errorCode) {
        return ERROR_CODE_MAPPINGS.getOrDefault(errorCode, errorCode);
    }

    private static final ImmutableMap<String, String> ERROR_CODE_MAPPINGS = ImmutableMap.<String, String>builder()
            .put(SHOPPING_CART_NOT_FOUND, SHOPPING_CART_NOT_FOUND_DESCRIPTION)
            .build();
}
