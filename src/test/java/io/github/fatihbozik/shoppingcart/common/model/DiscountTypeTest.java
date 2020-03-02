package io.github.fatihbozik.shoppingcart.common.model;

import io.github.fatihbozik.shoppingcart.discount.calculator.AmountCampaignDiscountCalculator;
import io.github.fatihbozik.shoppingcart.discount.calculator.RateCampaignDiscountCalculator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class DiscountTypeTest {

    @Test
    void shouldReturnRateDiscountCalculator() {
        assertThat(DiscountType.RATE.getCampaignDiscountCalculator().getClass(), is(RateCampaignDiscountCalculator.class));
    }

    @Test
    void shouldReturnAmountDiscountCalculator() {
        assertThat(DiscountType.AMOUNT.getCampaignDiscountCalculator().getClass(), is(AmountCampaignDiscountCalculator.class));
    }
}
