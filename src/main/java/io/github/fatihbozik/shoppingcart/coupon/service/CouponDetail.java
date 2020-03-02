package io.github.fatihbozik.shoppingcart.coupon.service;

import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import io.github.fatihbozik.shoppingcart.coupon.model.Coupon;
import io.github.fatihbozik.shoppingcart.coupon.model.CouponStatus;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CouponDetail {
    private final Long id;
    private final String title;
    private final BigDecimal minPurchaseAmount;
    private final BigDecimal discountValue;
    private final DiscountType discountType;
    private final CouponStatus status;

    public CouponDetail(final Coupon coupon) {
        this.id = coupon.getId();
        this.title = coupon.getTitle();
        this.minPurchaseAmount = coupon.getMinPurchaseAmount();
        this.discountValue = coupon.getDiscountValue();
        this.discountType = coupon.getDiscountType();
        this.status = coupon.getStatus();
    }
}
