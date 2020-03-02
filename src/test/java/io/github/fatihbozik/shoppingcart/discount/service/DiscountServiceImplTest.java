package io.github.fatihbozik.shoppingcart.discount.service;

import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartService;
import io.github.fatihbozik.shoppingcart.discount.rule.CampaignDiscountRule;
import io.github.fatihbozik.shoppingcart.discount.rule.CouponDiscountRule;
import io.github.fatihbozik.shoppingcart.discount.rule.DiscountRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountServiceImplTest {
    private DiscountServiceImpl discountService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private CampaignDiscountRule campaignDiscountRule;

    @Mock
    private CouponDiscountRule couponDiscountRule;

    private List<DiscountRule> discountRules = new ArrayList<>();

    @BeforeEach
    void setUp() {
        discountRules.add(campaignDiscountRule);
        discountRules.add(couponDiscountRule);
        discountService = new DiscountServiceImpl(discountRules, shoppingCartService);
        Mockito.mockitoSession().initMocks(discountService);
    }

    @Test
    void shouldInvokeAllDiscountRulesSuccessfully() {
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        when(shoppingCartService.getShoppingCartById(1L)).thenReturn(new ShoppingCartDetail(shoppingCart));

        discountService.applyDiscounts(1L);
        verify(shoppingCartService, times(1)).getShoppingCartById(1L);
        verify(campaignDiscountRule, times(1)).apply(any(ShoppingCartDetail.class));
        verify(couponDiscountRule, times(1)).apply(any(ShoppingCartDetail.class));
    }
}
