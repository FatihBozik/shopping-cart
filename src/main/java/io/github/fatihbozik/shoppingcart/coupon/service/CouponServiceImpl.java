package io.github.fatihbozik.shoppingcart.coupon.service;

import io.github.fatihbozik.shoppingcart.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;

}
