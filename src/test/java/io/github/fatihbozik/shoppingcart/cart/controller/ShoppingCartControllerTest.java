package io.github.fatihbozik.shoppingcart.cart.controller;

import com.google.common.collect.Sets;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.discount.service.DiscountService;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartControllerTest {
    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @Mock
    private DiscountService discountService;

    @Test
    void shouldPrintShoppingCartSuccessfully() {
        final Category category = new Category();
        category.setId(1L);
        category.setParent(null);
        category.setTitle("Computers");

        final Product product = new Product();
        product.setId(1L);
        product.setCategory(category);
        product.setTitle("Apple AirPods");
        product.setPrice(BigDecimal.valueOf(800));

        final ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(1L);
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(1);

        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);

        shoppingCart.setDeliveryCost(BigDecimal.ZERO);
        shoppingCart.setItems(Sets.newHashSet(shoppingCartItem));
        shoppingCart.setTotalPrice(BigDecimal.valueOf(800));
        ShoppingCartDetail shoppingCartDetail = new ShoppingCartDetail(shoppingCart);
        when(discountService.applyDiscounts(1L)).thenReturn(shoppingCartDetail);

        ShoppingCartDetail result = shoppingCartController.printShoppingCart(1L);
        assertThat(result.getDeliveryCost(), Matchers.comparesEqualTo(BigDecimal.valueOf(9.99)));
    }
}
