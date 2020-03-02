package io.github.fatihbozik.shoppingcart.common.model;

import io.github.fatihbozik.shoppingcart.discount.calculator.AmountCampaignDiscountCalculator;
import io.github.fatihbozik.shoppingcart.discount.calculator.AmountCouponDiscountCalculator;
import io.github.fatihbozik.shoppingcart.discount.calculator.RateCampaignDiscountCalculator;
import io.github.fatihbozik.shoppingcart.discount.calculator.RateCouponDiscountCalculator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class DiscountTypeTest {

    @Test
    void shouldReturnRateCampaignDiscountCalculator() {
        assertThat(DiscountType.RATE.getCampaignDiscountCalculator().getClass(), is(RateCampaignDiscountCalculator.class));
    }

    @Test
    void shouldReturnAmountCampaignDiscountCalculator() {
        assertThat(DiscountType.AMOUNT.getCampaignDiscountCalculator().getClass(), is(AmountCampaignDiscountCalculator.class));
    }

    @Test
    void shouldReturnRateCouponDiscountCalculator() {
        assertThat(DiscountType.RATE.getCouponDiscountCalculator().getClass(), is(RateCouponDiscountCalculator.class));
    }

    @Test
    void shouldReturnAmountCouponDiscountCalculator() {
        assertThat(DiscountType.AMOUNT.getCouponDiscountCalculator().getClass(), is(AmountCouponDiscountCalculator.class));
    }
}
