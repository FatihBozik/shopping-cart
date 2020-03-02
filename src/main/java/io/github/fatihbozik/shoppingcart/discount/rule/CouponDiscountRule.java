package io.github.fatihbozik.shoppingcart.discount.rule;

import io.github.fatihbozik.shoppingcart.cart.service.ApplyCouponRequest;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartService;
import io.github.fatihbozik.shoppingcart.coupon.service.CouponDetail;
import io.github.fatihbozik.shoppingcart.coupon.service.CouponService;
import io.github.fatihbozik.shoppingcart.discount.calculator.CouponDiscountCalculator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

import static io.github.fatihbozik.shoppingcart.util.Constants.COUPON_DISCOUNT_RULE_ORDER;

@Component
@RequiredArgsConstructor
@Order(COUPON_DISCOUNT_RULE_ORDER)
public class CouponDiscountRule implements DiscountRule {
    private static final Logger LOG = LoggerFactory.getLogger(CouponDiscountRule.class);

    private final CouponService couponService;
    private final ShoppingCartService shoppingCartService;

    @Override
    public void apply(ShoppingCartDetail shoppingCart) {
        final BigDecimal totalAmountBeforeCoupons = shoppingCart.getSumOfTheTotalPricesOfTheItems();
        List<CouponDetail> coupons = couponService.getActiveCouponsByMinPurchaseAmountGreaterThan(totalAmountBeforeCoupons);
        if (CollectionUtils.isEmpty(coupons)) {
            LOG.info("No coupons suitable for your shopping cart::{}", shoppingCart.getId());
            return;
        }
        final BigDecimal maxAmountOfCouponDiscount = coupons.stream()
                .map(coupon -> calculateDiscount(shoppingCart, coupon))
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        shoppingCart.setCouponDiscount(maxAmountOfCouponDiscount);
        shoppingCart.calculateAndSetTotalPrice();
        shoppingCartService.applyCoupon(new ApplyCouponRequest(shoppingCart));
    }

    private BigDecimal calculateDiscount(ShoppingCartDetail shoppingCartItem, CouponDetail coupon) {
        final CouponDiscountCalculator discountCalculator = coupon.getDiscountType().getCouponDiscountCalculator();
        return discountCalculator.calculateDiscount(shoppingCartItem, coupon);
    }
}
