package io.github.fatihbozik.shoppingcart.discount.calculator;

import io.github.fatihbozik.shoppingcart.campaign.service.CampaignDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartItemDetail;

import java.math.BigDecimal;

public interface CampaignDiscountCalculator {
    BigDecimal calculateDiscount(ShoppingCartItemDetail shoppingCartItem, CampaignDetail campaign);
}
