package io.github.fatihbozik.shoppingcart.discount.service;

import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;

public interface DiscountService {
    ShoppingCartDetail applyDiscounts(Long shoppingCartId);
}
