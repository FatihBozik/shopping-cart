package io.github.fatihbozik.shoppingcart.cart.service;

import com.google.common.collect.Sets;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.cart.repository.ShoppingCartRepository;
import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.discount.rule.CampaignDiscountRule;
import io.github.fatihbozik.shoppingcart.discount.rule.CouponDiscountRule;
import io.github.fatihbozik.shoppingcart.discount.rule.DiscountRule;
import io.github.fatihbozik.shoppingcart.fault.ServiceRuntimeException;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CampaignDiscountRule campaignDiscountRule;

    @Mock
    private CouponDiscountRule couponDiscountRule;

    private List<DiscountRule> discountRules = new ArrayList<>();

    @BeforeEach
    void setUp() {
        discountRules.add(campaignDiscountRule);
        discountRules.add(couponDiscountRule);
        shoppingCartService = new ShoppingCartServiceImpl(shoppingCartRepository, discountRules);
        Mockito.mockitoSession().initMocks(shoppingCartService);
    }

    @Test
    void shouldThrowShoppingCartNotFoundExceptionWhenShoppingCartNotFound() {
        when(shoppingCartRepository.findShoppingCartById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ServiceRuntimeException.class, () -> shoppingCartService.getShoppingCartById(1L));
        verify(shoppingCartRepository, times(1)).findShoppingCartById(1L);
    }

    @Test
    void shouldGetShoppingCartSuccessfully() {
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        when(shoppingCartRepository.findShoppingCartById(1L)).thenReturn(Optional.of(shoppingCart));

        final ShoppingCartDetail shoppingCartDetail = shoppingCartService.getShoppingCartById(1L);
        assertThat(shoppingCartDetail.getId(), is(1L));
        verify(shoppingCartRepository, times(1)).findShoppingCartById(1L);
    }

    @Test
    void shouldInvokeAllDiscountRulesSuccessfully() {
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        when(shoppingCartRepository.findShoppingCartById(1L)).thenReturn(Optional.of(shoppingCart));

        shoppingCartService.applyDiscounts(1L);
        verify(shoppingCartRepository, times(1)).findShoppingCartById(1L);
        verify(campaignDiscountRule, times(1)).apply(any(ShoppingCartDetail.class));
        verify(couponDiscountRule, times(1)).apply(any(ShoppingCartDetail.class));
    }

    @Test
    void shouldUpdateShoppingCartSuccessfully() {
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
        shoppingCartItem.setSubTotalPrice(BigDecimal.valueOf(15_000L));
        shoppingCartItem.setTotalPrice(BigDecimal.valueOf(15_000L));

        final ShoppingCart shoppingCartInDb = new ShoppingCart();
        shoppingCartInDb.setId(1L);
        shoppingCartInDb.setTotalPrice(BigDecimal.valueOf(15_000L));
        shoppingCartInDb.setItems(Sets.newHashSet(shoppingCartItem));
        shoppingCartItem.setShoppingCart(shoppingCartInDb);
        when(shoppingCartRepository.getOne(1L)).thenReturn(shoppingCartInDb);
        when(shoppingCartRepository.saveAndFlush(any(ShoppingCart.class))).thenReturn(shoppingCartInDb);


        final ShoppingCartItem updateShoppingCartItem = new ShoppingCartItem();
        updateShoppingCartItem.setId(1L);
        updateShoppingCartItem.setUnitPrice(BigDecimal.valueOf(5));
        updateShoppingCartItem.setProduct(macBookPro);
        updateShoppingCartItem.setUnitPrice(BigDecimal.valueOf(15_000L));
        updateShoppingCartItem.setQuantity(1);
        updateShoppingCartItem.setCampaignDiscount(BigDecimal.valueOf(200L));
        updateShoppingCartItem.setSubTotalPrice(BigDecimal.valueOf(15_000L));
        updateShoppingCartItem.setTotalPrice(BigDecimal.valueOf(14_800L));

        final ShoppingCart updateShoppingCart = new ShoppingCart();
        updateShoppingCart.setId(1L);
        updateShoppingCart.setTotalPrice(BigDecimal.valueOf(14_800L));
        updateShoppingCart.setItems(Sets.newHashSet(updateShoppingCartItem));
        updateShoppingCartItem.setShoppingCart(updateShoppingCart);

        final ShoppingCartDetail shoppingCartDetail = new ShoppingCartDetail(updateShoppingCart);
        ShoppingCartDetail updatedShoppingCart = shoppingCartService.updateShoppingCart(new UpdateShoppingCartCommand(shoppingCartDetail));
        ShoppingCartItemDetail shoppingCartItemDetail = updatedShoppingCart.getItems().iterator().next();

        assertThat(updatedShoppingCart.getId(), is(1L));
        assertThat(updatedShoppingCart.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(14_800L)));
        assertThat(shoppingCartItemDetail.getId(), is(1L));
        assertThat(shoppingCartItemDetail.getCampaignDiscount(), Matchers.comparesEqualTo(BigDecimal.valueOf(200L)));
        assertThat(shoppingCartItemDetail.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(14_800L)));
    }
}
