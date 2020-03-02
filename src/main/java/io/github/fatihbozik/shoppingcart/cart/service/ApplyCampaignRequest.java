package io.github.fatihbozik.shoppingcart.cart.service;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ApplyCampaignRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final BigDecimal totalPrice;
    private Set<UpdateShoppingCartItemRequest> updateShoppingCartItemRequests;

    public ApplyCampaignRequest(ShoppingCartDetail shoppingCart) {
        this.id = shoppingCart.getId();
        this.totalPrice = shoppingCart.getTotalPrice();
        this.updateShoppingCartItemRequests = shoppingCart.getItems().stream().map(UpdateShoppingCartItemRequest::new).collect(Collectors.toSet());
    }
}
