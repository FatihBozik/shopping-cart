package io.github.fatihbozik.shoppingcart.cart.service;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ApplyCouponRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long shoppingCartId;
    private final BigDecimal couponDiscount;
    private final BigDecimal totalPrice;

    public ApplyCouponRequest(ShoppingCartDetail shoppingCartDetail) {
        this.shoppingCartId = shoppingCartDetail.getId();
        this.couponDiscount = shoppingCartDetail.getCouponDiscount();
        this.totalPrice = shoppingCartDetail.getTotalPrice();
    }
}
