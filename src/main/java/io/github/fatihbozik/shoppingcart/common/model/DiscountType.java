package io.github.fatihbozik.shoppingcart.common.model;

import io.github.fatihbozik.shoppingcart.discount.calculator.AmountDiscountCalculator;
import io.github.fatihbozik.shoppingcart.discount.calculator.DiscountCalculator;
import io.github.fatihbozik.shoppingcart.discount.calculator.RateDiscountCalculator;

public enum DiscountType {
    RATE {
        @Override
        public DiscountCalculator getCalculator() {
            return new RateDiscountCalculator();
        }
    },
    AMOUNT {
        @Override
        public DiscountCalculator getCalculator() {
            return new AmountDiscountCalculator();
        }
    };

    public abstract DiscountCalculator getCalculator();
}
