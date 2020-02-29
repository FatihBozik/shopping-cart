package io.github.fatihbozik.shoppingcart.coupon.service;

import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import io.github.fatihbozik.shoppingcart.coupon.model.Coupon;

import java.math.BigDecimal;

public class CouponDetail {
    private final Long id;
    private final String title;
    private final BigDecimal minPurchaseAmount;
    private final BigDecimal discountValue;
    private final DiscountType discountType;

    public CouponDetail(final Coupon coupon) {
        this.id = coupon.getId();
        this.title = coupon.getTitle();
        this.minPurchaseAmount = coupon.getMinPurchaseAmount();
        this.discountValue = coupon.getDiscountValue();
        this.discountType = coupon.getDiscountType();
    }
}
