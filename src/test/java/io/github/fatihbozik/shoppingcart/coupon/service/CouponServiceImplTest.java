package io.github.fatihbozik.shoppingcart.coupon.service;

import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import io.github.fatihbozik.shoppingcart.coupon.model.Coupon;
import io.github.fatihbozik.shoppingcart.coupon.model.CouponStatus;
import io.github.fatihbozik.shoppingcart.coupon.repository.CouponRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTest {
    @InjectMocks
    private CouponServiceImpl couponService;

    @Mock
    private CouponRepository couponRepository;

    @Test
    void shouldGetActiveCouponsSuccessfullyByMinPurchaseAmountGreaterThan() {
        final Coupon coupon1 = new Coupon();
        coupon1.setId(1L);
        coupon1.setTitle("Shopping Cart 20% Coupon minimum purchase amount 140 TL");
        coupon1.setStatus(CouponStatus.ACTIVE);
        coupon1.setDiscountType(DiscountType.RATE);
        coupon1.setDiscountValue(BigDecimal.valueOf(20L));
        coupon1.setMinPurchaseAmount(BigDecimal.valueOf(140L));

        when(couponRepository.findCouponsByStatusAndMinPurchaseAmountLessThanEqual(CouponStatus.ACTIVE, BigDecimal.valueOf(150L)))
                .thenReturn(Collections.singletonList(coupon1));

        List<CouponDetail> couponDetails = couponService.getActiveCouponsByMinPurchaseAmountLessThanEqual(BigDecimal.valueOf(150L));
        CouponDetail couponDetail = couponDetails.get(0);
        assertThat(couponDetails.size(), is(1));
        assertThat(couponDetail.getId(), is(1L));
        assertThat(couponDetail.getTitle(), is("Shopping Cart 20% Coupon minimum purchase amount 140 TL"));
        assertThat(couponDetail.getStatus(), is(CouponStatus.ACTIVE));
        assertThat(couponDetail.getDiscountType(), is(DiscountType.RATE));
        assertThat(couponDetail.getDiscountValue(), Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
        assertThat(couponDetail.getMinPurchaseAmount(), Matchers.comparesEqualTo(BigDecimal.valueOf(140)));
    }
}
