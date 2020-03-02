package io.github.fatihbozik.shoppingcart.common.math;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

class PercentageTest {

    @Test
    void shouldCalculatePercentageParameter() {
        BigDecimal result = new Percentage(BigDecimal.valueOf(100), BigDecimal.valueOf(45)).getValue();
        assertThat(result, Matchers.comparesEqualTo(BigDecimal.valueOf(45)));
    }

    @Test
    void shouldCalculatePercentageWithDoubleParameter() {
        BigDecimal result = new Percentage(100, 20).getValue();
        assertThat(result, Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
    }
}
