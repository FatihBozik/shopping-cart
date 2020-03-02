package io.github.fatihbozik.shoppingcart.discount.calculator;

import com.google.common.collect.Sets;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import io.github.fatihbozik.shoppingcart.coupon.model.Coupon;
import io.github.fatihbozik.shoppingcart.coupon.model.CouponStatus;
import io.github.fatihbozik.shoppingcart.coupon.service.CouponDetail;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

class RateCouponDiscountCalculatorTest {
    private final RateCouponDiscountCalculator rateCouponDiscountCalculator = new RateCouponDiscountCalculator();

    @Test
    void shouldCalculateDiscount() {
        final Category category = new Category();
        category.setId(1L);

        final Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setDiscountValue(BigDecimal.valueOf(20));
        coupon.setDiscountType(DiscountType.RATE);
        coupon.setStatus(CouponStatus.ACTIVE);

        final ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setUnitPrice(BigDecimal.valueOf(30));
        shoppingCartItem.setQuantity(2);
        shoppingCartItem.setTotalPrice(BigDecimal.valueOf(60));
        shoppingCartItem.setCampaignDiscount(BigDecimal.ZERO);
        Product product = new Product();
        product.setId(1L);
        product.setCategory(category);
        shoppingCartItem.setProduct(product);

        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(Sets.newHashSet(shoppingCartItem));
        final ShoppingCartDetail shoppingCartDetail = new ShoppingCartDetail(shoppingCart);
        final CouponDetail couponDetail = new CouponDetail(coupon);
        final BigDecimal amountDiscount = rateCouponDiscountCalculator.calculateDiscount(shoppingCartDetail, couponDetail);
        assertThat(amountDiscount, Matchers.comparesEqualTo(BigDecimal.valueOf(12)));
    }
}
