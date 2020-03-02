package io.github.fatihbozik.shoppingcart.cart.service;

import com.google.common.collect.Sets;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.cart.repository.ShoppingCartRepository;
import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.fault.ServiceRuntimeException;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

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
    void shouldApplyCampaignToShoppingCartSuccessfully() {
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
        ShoppingCartDetail updatedShoppingCart = shoppingCartService.applyCampaign(new ApplyCampaignRequest(shoppingCartDetail));
        ShoppingCartItemDetail shoppingCartItemDetail = updatedShoppingCart.getItems().iterator().next();

        assertThat(updatedShoppingCart.getId(), is(1L));
        assertThat(updatedShoppingCart.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(14_800L)));
        assertThat(shoppingCartItemDetail.getId(), is(1L));
        assertThat(shoppingCartItemDetail.getCampaignDiscount(), Matchers.comparesEqualTo(BigDecimal.valueOf(200L)));
        assertThat(shoppingCartItemDetail.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(14_800L)));
    }

    @Test
    void shouldApplyCouponToShoppingCartSuccessfully() {
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


        final ShoppingCart shoppingCartAfterCoupon = new ShoppingCart();
        shoppingCartAfterCoupon.setId(1L);
        shoppingCartAfterCoupon.setCouponDiscount(BigDecimal.valueOf(500L));
        shoppingCartAfterCoupon.setTotalPrice(BigDecimal.valueOf(14_500L));
        final ShoppingCartDetail shoppingCartDetailAfterCoupon = new ShoppingCartDetail(shoppingCartAfterCoupon);
        ShoppingCartDetail shoppingCartDetail = shoppingCartService.applyCoupon(new ApplyCouponRequest(shoppingCartDetailAfterCoupon));

        assertThat(shoppingCartDetail.getId(), is(1L));
        assertThat(shoppingCartDetail.getCouponDiscount(), Matchers.comparesEqualTo(BigDecimal.valueOf(500L)));
        assertThat(shoppingCartDetail.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(14_500L)));
    }
}
