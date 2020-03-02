package io.github.fatihbozik.shoppingcart.cart.service;

public interface ShoppingCartService {
    ShoppingCartDetail getShoppingCartById(Long shoppingCartId);

    ShoppingCartDetail updateShoppingCart(UpdateShoppingCartCommand updateShoppingCartCommand);

    void applyDiscounts(Long shoppingCartId);
}
