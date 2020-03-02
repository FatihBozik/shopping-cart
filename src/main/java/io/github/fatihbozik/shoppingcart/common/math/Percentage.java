package io.github.fatihbozik.shoppingcart.common.math;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

public class Percentage {
    private static final int POWER_OF_TEN = -2;
    private final BigDecimal value;

    public Percentage(double base, double rate) {
        this(valueOf(base), valueOf(rate));
    }

    public Percentage(BigDecimal base, BigDecimal rate) {
        this.value = base.multiply(rate).scaleByPowerOfTen(POWER_OF_TEN);
    }

    public BigDecimal getValue() {
        return value;
    }
}
