package io.github.fatihbozik.shoppingcart.discount.calculator;

import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import io.github.fatihbozik.shoppingcart.coupon.model.Coupon;
import io.github.fatihbozik.shoppingcart.coupon.service.CouponDetail;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

class AmountCouponDiscountCalculatorTest {
    private AmountCouponDiscountCalculator amountCouponDiscountCalculator = new AmountCouponDiscountCalculator();

    @Test
    void shouldCalculateDiscount() {
        final Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setDiscountValue(BigDecimal.valueOf(20));
        coupon.setDiscountType(DiscountType.AMOUNT);

        final BigDecimal amountDiscount = amountCouponDiscountCalculator.calculateDiscount(null, new CouponDetail(coupon));
        assertThat(amountDiscount, Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
    }
}
