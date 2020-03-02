package io.github.fatihbozik.shoppingcart.discount.calculator;

import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.coupon.service.CouponDetail;

import java.math.BigDecimal;

public class AmountCouponDiscountCalculator implements CouponDiscountCalculator {

    @Override
    public BigDecimal calculateDiscount(ShoppingCartDetail shoppingCart, CouponDetail coupon) {
        return coupon.getDiscountValue();
    }
}
