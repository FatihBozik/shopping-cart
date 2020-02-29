package io.github.fatihbozik.shoppingcart.coupon.repository;

import io.github.fatihbozik.shoppingcart.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
