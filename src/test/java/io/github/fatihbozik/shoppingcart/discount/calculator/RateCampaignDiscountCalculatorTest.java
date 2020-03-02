package io.github.fatihbozik.shoppingcart.discount.calculator;

import io.github.fatihbozik.shoppingcart.campaign.model.Campaign;
import io.github.fatihbozik.shoppingcart.campaign.service.CampaignDetail;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartItemDetail;
import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

class RateCampaignDiscountCalculatorTest {
    private final RateCampaignDiscountCalculator rateCampaignDiscountCalculator = new RateCampaignDiscountCalculator();

    @Test
    void shouldCalculateDiscount() {
        final Category category = new Category();
        category.setId(1L);

        final Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setDiscountValue(BigDecimal.valueOf(20));
        campaign.setDiscountType(DiscountType.RATE);
        campaign.setCategory(category);

        final ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setUnitPrice(BigDecimal.valueOf(30));
        shoppingCartItem.setQuantity(2);
        Product product = new Product();
        product.setId(1L);
        product.setCategory(category);
        shoppingCartItem.setProduct(product);

        final ShoppingCartItemDetail shoppingCartItemDetail = new ShoppingCartItemDetail(shoppingCartItem);
        final CampaignDetail campaignDetail = new CampaignDetail(campaign);
        final BigDecimal discount = rateCampaignDiscountCalculator.calculateDiscount(shoppingCartItemDetail, campaignDetail);
        assertThat(discount, Matchers.comparesEqualTo(BigDecimal.valueOf(12)));
    }
}
