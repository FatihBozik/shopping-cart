package io.github.fatihbozik.shoppingcart.cart.service;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UpdateShoppingCartItemCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final BigDecimal campaignDiscount;
    private final BigDecimal totalPrice;

    public UpdateShoppingCartItemCommand(ShoppingCartItemDetail shoppingCartItem) {
        this.id = shoppingCartItem.getId();
        this.campaignDiscount = shoppingCartItem.getCampaignDiscount();
        this.totalPrice = shoppingCartItem.getTotalPrice();
    }
}
