package io.github.fatihbozik.shoppingcart.common.model;

import io.github.fatihbozik.shoppingcart.discount.calculator.*;

public enum DiscountType {
    RATE {
        @Override
        public CampaignDiscountCalculator getCampaignDiscountCalculator() {
            return new RateCampaignDiscountCalculator();
        }

        @Override
        public CouponDiscountCalculator getCouponDiscountCalculator() {
            return new RateCouponDiscountCalculator();
        }
    },
    AMOUNT {
        @Override
        public CampaignDiscountCalculator getCampaignDiscountCalculator() {
            return new AmountCampaignDiscountCalculator();
        }

        @Override
        public CouponDiscountCalculator getCouponDiscountCalculator() {
            return new AmountCouponDiscountCalculator();
        }
    };

    public abstract CampaignDiscountCalculator getCampaignDiscountCalculator();

    public abstract CouponDiscountCalculator getCouponDiscountCalculator();
}
