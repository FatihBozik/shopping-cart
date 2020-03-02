package io.github.fatihbozik.shoppingcart.discount.calculator;

import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.common.math.Percentage;
import io.github.fatihbozik.shoppingcart.coupon.service.CouponDetail;

import java.math.BigDecimal;

public class RateCouponDiscountCalculator implements CouponDiscountCalculator {

    @Override
    public BigDecimal calculateDiscount(ShoppingCartDetail shoppingCart, CouponDetail coupon) {
        BigDecimal subTotal = shoppingCart.getSumOfTheTotalPricesOfTheItems();
        return new Percentage(subTotal, coupon.getDiscountValue()).getValue();
    }
}
