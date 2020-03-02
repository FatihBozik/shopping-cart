package io.github.fatihbozik.shoppingcart.discount.rule;

import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static io.github.fatihbozik.shoppingcart.util.Constants.COUPON_DISCOUNT_RULE_ORDER;

@Component
@Order(COUPON_DISCOUNT_RULE_ORDER)
public class CouponDiscountRule implements DiscountRule {

    @Override
    public void apply(ShoppingCartDetail shoppingCart) {

    }
}
