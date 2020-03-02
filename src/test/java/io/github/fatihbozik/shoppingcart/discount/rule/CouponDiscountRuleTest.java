package io.github.fatihbozik.shoppingcart.discount.rule;

import com.google.common.collect.Sets;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.cart.service.ApplyCouponRequest;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartService;
import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import io.github.fatihbozik.shoppingcart.coupon.model.Coupon;
import io.github.fatihbozik.shoppingcart.coupon.model.CouponStatus;
import io.github.fatihbozik.shoppingcart.coupon.service.CouponDetail;
import io.github.fatihbozik.shoppingcart.coupon.service.CouponService;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponDiscountRuleTest {
    @InjectMocks
    private CouponDiscountRule couponDiscountRule;

    @Mock
    private CouponService couponService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Test
    void shouldNotApplyCouponIfNotSuitable() {
        final Category computersCategory = new Category();
        computersCategory.setId(1L);
        computersCategory.setTitle("Computers");

        final Product macBookPro = new Product();
        macBookPro.setId(1L);
        macBookPro.setTitle("New MacBook Pro 16 inch");
        macBookPro.setCategory(computersCategory);

        final ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(1L);
        shoppingCartItem.setUnitPrice(BigDecimal.valueOf(5));
        shoppingCartItem.setProduct(macBookPro);
        shoppingCartItem.setUnitPrice(BigDecimal.valueOf(15_000L));
        shoppingCartItem.setQuantity(1);
        shoppingCartItem.setTotalPrice(BigDecimal.valueOf(15_000L));

        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(Sets.newHashSet(shoppingCartItem));
        shoppingCart.setTotalPrice(BigDecimal.valueOf(15_000L));
        when(couponService.getActiveCouponsByMinPurchaseAmountGreaterThan(BigDecimal.valueOf(15_000L)))
                .thenReturn(Collections.emptyList());

        couponDiscountRule.apply(new ShoppingCartDetail(shoppingCart));

        verifyNoInteractions(shoppingCartService);
    }

    @Test
    void shouldApplyCouponSuccessfully() {
        final Category computersCategory = new Category();
        computersCategory.setId(1L);
        computersCategory.setTitle("Computers");

        final Product macBookPro = new Product();
        macBookPro.setId(1L);
        macBookPro.setTitle("New MacBook Pro 16 inch");
        macBookPro.setCategory(computersCategory);

        final ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(1L);
        shoppingCartItem.setUnitPrice(BigDecimal.valueOf(5));
        shoppingCartItem.setProduct(macBookPro);
        shoppingCartItem.setUnitPrice(BigDecimal.valueOf(15_000L));
        shoppingCartItem.setQuantity(1);
        shoppingCartItem.setTotalPrice(BigDecimal.valueOf(15_000L));

        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(Sets.newHashSet(shoppingCartItem));
        shoppingCart.setTotalPrice(BigDecimal.valueOf(15_000L));

        final Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setTitle("10% discount over 100 TL");
        coupon.setDiscountValue(BigDecimal.valueOf(10));
        coupon.setDiscountType(DiscountType.RATE);
        coupon.setMinPurchaseAmount(BigDecimal.valueOf(14_000));
        coupon.setStatus(CouponStatus.ACTIVE);
        when(couponService.getActiveCouponsByMinPurchaseAmountGreaterThan(BigDecimal.valueOf(15_000L)))
                .thenReturn(Collections.singletonList(new CouponDetail(coupon)));

        couponDiscountRule.apply(new ShoppingCartDetail(shoppingCart));

        ArgumentCaptor<ApplyCouponRequest> applyCouponRequestArgumentCaptor = ArgumentCaptor.forClass(ApplyCouponRequest.class);
        verify(shoppingCartService, times(1)).applyCoupon(applyCouponRequestArgumentCaptor.capture());

        ApplyCouponRequest applyCouponRequest = applyCouponRequestArgumentCaptor.getValue();
        assertThat(applyCouponRequest.getShoppingCartId(), is(1L));
        assertThat(applyCouponRequest.getCouponDiscount(), Matchers.comparesEqualTo(BigDecimal.valueOf(1_500L)));
        assertThat(applyCouponRequest.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(13_500L)));
    }
}
