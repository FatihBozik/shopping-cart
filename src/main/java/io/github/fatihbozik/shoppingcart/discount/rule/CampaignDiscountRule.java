package io.github.fatihbozik.shoppingcart.discount.rule;

import io.github.fatihbozik.shoppingcart.campaign.service.CampaignDetail;
import io.github.fatihbozik.shoppingcart.campaign.service.CampaignService;
import io.github.fatihbozik.shoppingcart.cart.service.ApplyCampaignRequest;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartItemDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartService;
import io.github.fatihbozik.shoppingcart.category.service.CategoryDetail;
import io.github.fatihbozik.shoppingcart.discount.calculator.CampaignDiscountCalculator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

import static io.github.fatihbozik.shoppingcart.util.Constants.CAMPAIGN_DISCOUNT_RULE_ORDER;

@Component
@Order(CAMPAIGN_DISCOUNT_RULE_ORDER)
@RequiredArgsConstructor
public class CampaignDiscountRule implements DiscountRule {
    private static final Logger LOG = LoggerFactory.getLogger(CampaignDiscountRule.class);

    private final CampaignService campaignService;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public void apply(ShoppingCartDetail shoppingCart) {
        if (CollectionUtils.isEmpty(shoppingCart.getItems())) {
            LOG.info("Shopping cart is empty::{}", shoppingCart.getId());
            return;
        }
        shoppingCart.getItems().forEach(this::applyCampaignDiscount);
        shoppingCart.calculateAndSetTotalPrice();

        shoppingCartService.applyCampaign(new ApplyCampaignRequest(shoppingCart));
    }

    private void applyCampaignDiscount(ShoppingCartItemDetail shoppingCartItem) {
        final CategoryDetail category = shoppingCartItem.getProduct().getCategory();
        List<CampaignDetail> campaigns = campaignService.getCampaignsByCategoryId(category.getId());
        if (CollectionUtils.isEmpty(campaigns)) {
            LOG.info("No campaign suitable for your shopping cart item::{}", shoppingCartItem.getId());
            return;
        }
        final BigDecimal maxAmountOfCampaignDiscount = campaigns.stream()
                .filter(campaign -> shoppingCartItem.getQuantity() > campaign.getItemThreshold())
                .map(campaign -> calculateDiscount(shoppingCartItem, campaign))
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        shoppingCartItem.setCampaignDiscount(maxAmountOfCampaignDiscount);
        shoppingCartItem.calculateAndSetTotalPrice();
    }

    private BigDecimal calculateDiscount(ShoppingCartItemDetail shoppingCartItem, CampaignDetail campaign) {
        final CampaignDiscountCalculator discountCalculator = campaign.getDiscountType().getCampaignDiscountCalculator();
        return discountCalculator.calculateDiscount(shoppingCartItem, campaign);
    }
}
