package io.github.fatihbozik.shoppingcart.discount.calculator;

import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.coupon.service.CouponDetail;

import java.math.BigDecimal;

public interface CouponDiscountCalculator {
    BigDecimal calculateDiscount(ShoppingCartDetail shoppingCart, CouponDetail coupon);
}
