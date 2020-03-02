package io.github.fatihbozik.shoppingcart.discount.rule;

import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;

public interface DiscountRule {

    void apply(ShoppingCartDetail shoppingCart);
}
