package io.github.fatihbozik.shoppingcart.coupon.service;

import io.github.fatihbozik.shoppingcart.coupon.model.Coupon;
import io.github.fatihbozik.shoppingcart.coupon.model.CouponStatus;
import io.github.fatihbozik.shoppingcart.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CouponDetail> getActiveCouponsByMinPurchaseAmountGreaterThan(BigDecimal minPurchaseAmount) {
        List<Coupon> coupons = couponRepository.findCouponsByStatusAndMinPurchaseAmountGreaterThan(CouponStatus.ACTIVE, minPurchaseAmount);
        return coupons.stream().map(CouponDetail::new).collect(Collectors.toList());
    }
}
