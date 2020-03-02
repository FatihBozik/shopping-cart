package io.github.fatihbozik.shoppingcart.common.model;

import io.github.fatihbozik.shoppingcart.discount.calculator.AmountDiscountCalculator;
import io.github.fatihbozik.shoppingcart.discount.calculator.RateDiscountCalculator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class DiscountTypeTest {

    @Test
    void shouldReturnRateDiscountCalculator() {
        assertThat(DiscountType.RATE.getCalculator().getClass(), is(RateDiscountCalculator.class));
    }

    @Test
    void shouldReturnAmountDiscountCalculator() {
        assertThat(DiscountType.AMOUNT.getCalculator().getClass(), is(AmountDiscountCalculator.class));
    }
}
