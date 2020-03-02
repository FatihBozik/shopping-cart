package io.github.fatihbozik.shoppingcart.deliverycost;

import com.google.common.collect.Sets;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

class DeliveryCostCalculatorTest {
    private DeliveryCostCalculator deliveryCostCalculator;

    @BeforeEach
    void beforeEach() {
        deliveryCostCalculator = new DeliveryCostCalculator(BigDecimal.valueOf(1), BigDecimal.valueOf(5), BigDecimal.valueOf(2.99));
    }

    @Test
    void shouldCalculateDeliveryCostSuccessfully() {
        final Category electronicsCategory = new Category();
        electronicsCategory.setId(1L);
        electronicsCategory.setTitle("Electronics");

        final Product sonyHeadphone = new Product();
        sonyHeadphone.setId(1L);
        sonyHeadphone.setTitle("Sony MDRXB50AP Extra Bass Earbud Headset");
        sonyHeadphone.setCategory(electronicsCategory);

        final Category computersCategory = new Category();
        computersCategory.setId(2L);
        computersCategory.setTitle("Computers");

        final Product macBookPro = new Product();
        macBookPro.setId(2L);
        macBookPro.setTitle("New MacBook Pro 16 inch");
        macBookPro.setCategory(computersCategory);

        final Product dellXps = new Product();
        dellXps.setId(3L);
        dellXps.setTitle("Dell XPS 15 inch");
        dellXps.setCategory(computersCategory);

        final ShoppingCartItem sonyHeadphoneItem = new ShoppingCartItem();
        sonyHeadphoneItem.setId(1L);
        sonyHeadphoneItem.setProduct(sonyHeadphone);
        sonyHeadphoneItem.setQuantity(3);
        sonyHeadphoneItem.setUnitPrice(sonyHeadphone.getPrice());

        final ShoppingCartItem macBookProItem = new ShoppingCartItem();
        macBookProItem.setId(2L);
        macBookProItem.setProduct(macBookPro);
        macBookProItem.setQuantity(1);
        macBookProItem.setUnitPrice(macBookPro.getPrice());

        final ShoppingCartItem dellXpsItem = new ShoppingCartItem();
        dellXpsItem.setId(3L);
        dellXpsItem.setProduct(dellXps);
        dellXpsItem.setQuantity(1);
        dellXpsItem.setUnitPrice(dellXps.getPrice());

        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setCouponDiscount(BigDecimal.valueOf(12));
        shoppingCart.setItems(Sets.newHashSet(sonyHeadphoneItem, macBookProItem, dellXpsItem));

        BigDecimal deliveryCost = deliveryCostCalculator.calculateFor(new ShoppingCartDetail(shoppingCart));
        assertThat(deliveryCost, Matchers.comparesEqualTo(BigDecimal.valueOf(19.99)));
    }
}

