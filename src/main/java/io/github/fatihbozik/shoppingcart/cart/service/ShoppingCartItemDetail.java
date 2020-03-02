package io.github.fatihbozik.shoppingcart.cart.service;

import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.product.service.ProductDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ShoppingCartItemDetail {
    private final Long id;
    private final ProductDetail product;
    private final int quantity;
    private final BigDecimal unitPrice;
    private BigDecimal campaignDiscount;
    private final BigDecimal subTotalPrice;
    private BigDecimal totalPrice;

    public ShoppingCartItemDetail(ShoppingCartItem shoppingCartItem) {
        this.id = shoppingCartItem.getId();
        this.product = new ProductDetail(shoppingCartItem.getProduct());
        this.quantity = shoppingCartItem.getQuantity();
        this.unitPrice = shoppingCartItem.getUnitPrice();
        this.campaignDiscount = shoppingCartItem.getCampaignDiscount();
        this.subTotalPrice = shoppingCartItem.getSubTotalPrice();
        this.totalPrice = shoppingCartItem.getTotalPrice();
    }

    public void calculateAndSetTotalPrice() {
        this.totalPrice = subTotalPrice.subtract(campaignDiscount);
    }
}
