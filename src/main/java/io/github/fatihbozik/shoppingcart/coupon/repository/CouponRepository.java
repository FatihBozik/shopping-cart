package io.github.fatihbozik.shoppingcart.coupon.repository;

import io.github.fatihbozik.shoppingcart.coupon.model.Coupon;
import io.github.fatihbozik.shoppingcart.coupon.model.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findCouponsByStatusAndMinPurchaseAmountLessThanEqual(CouponStatus couponStatus, BigDecimal minPurchaseAmount);
}
