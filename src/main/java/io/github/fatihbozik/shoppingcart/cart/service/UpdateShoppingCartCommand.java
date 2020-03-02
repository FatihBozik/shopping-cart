package io.github.fatihbozik.shoppingcart.cart.service;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UpdateShoppingCartCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final BigDecimal totalPrice;
    private Set<UpdateShoppingCartItemCommand> updateShoppingCartItemCommands;

    public UpdateShoppingCartCommand(ShoppingCartDetail shoppingCart) {
        this.id = shoppingCart.getId();
        this.totalPrice = shoppingCart.getTotalPrice();
        this.updateShoppingCartItemCommands = shoppingCart.getItems().stream().map(UpdateShoppingCartItemCommand::new).collect(Collectors.toSet());
    }
}
