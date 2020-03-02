package io.github.fatihbozik.shoppingcart.coupon.service;

import java.math.BigDecimal;
import java.util.List;

public interface CouponService {

    List<CouponDetail> getActiveCouponsByMinPurchaseAmountLessThanEqual(BigDecimal minPurchaseAmount);
}
