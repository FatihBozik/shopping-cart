package io.github.fatihbozik.shoppingcart.discount.calculator;

import io.github.fatihbozik.shoppingcart.campaign.service.CampaignDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartItemDetail;

import java.math.BigDecimal;

public class AmountDiscountCalculator implements DiscountCalculator {

    @Override
    public BigDecimal calculateDiscount(ShoppingCartItemDetail shoppingCartItem, CampaignDetail campaign) {
        return campaign.getDiscountValue().multiply(BigDecimal.valueOf(shoppingCartItem.getQuantity()));
    }
}
