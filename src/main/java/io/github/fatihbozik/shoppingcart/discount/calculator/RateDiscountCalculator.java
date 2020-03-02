package io.github.fatihbozik.shoppingcart.discount.calculator;

import io.github.fatihbozik.shoppingcart.campaign.service.CampaignDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartItemDetail;
import io.github.fatihbozik.shoppingcart.common.math.Percentage;

import java.math.BigDecimal;

public class RateDiscountCalculator implements DiscountCalculator {

    @Override
    public BigDecimal calculateDiscount(ShoppingCartItemDetail shoppingCartItem, CampaignDetail campaign) {
        final BigDecimal discountPerProduct = new Percentage(shoppingCartItem.getUnitPrice(), campaign.getDiscountValue()).getValue();
        return discountPerProduct.multiply(BigDecimal.valueOf(shoppingCartItem.getQuantity()));
    }
}
