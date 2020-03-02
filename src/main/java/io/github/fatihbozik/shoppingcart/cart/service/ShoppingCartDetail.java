package io.github.fatihbozik.shoppingcart.cart.service;

import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class ShoppingCartDetail {
    private final Long id;
    private final Set<ShoppingCartItemDetail> items;
    private BigDecimal couponDiscount;
    private final BigDecimal deliveryCost;
    private BigDecimal totalPrice;

    public ShoppingCartDetail(ShoppingCart shoppingCart) {
        this.id = shoppingCart.getId();
        this.items = shoppingCart.getItems().stream().map(ShoppingCartItemDetail::new).collect(Collectors.toSet());
        this.couponDiscount = shoppingCart.getCouponDiscount();
        this.deliveryCost = shoppingCart.getDeliveryCost();
        this.totalPrice = shoppingCart.getTotalPrice();
    }

    public void calculateAndSetTotalPrice() {
        this.totalPrice = getSumOfTheTotalPricesOfTheItems().subtract(couponDiscount).add(deliveryCost);
    }

    public BigDecimal getCampaignDiscount() {
        return items.stream()
                .map(ShoppingCartItemDetail::getCampaignDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getSumOfTheTotalPricesOfTheItems() {
        return items.stream()
                .map(ShoppingCartItemDetail::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
