package io.github.fatihbozik.shoppingcart.discount.rule;

import io.github.fatihbozik.shoppingcart.campaign.service.CampaignDetail;
import io.github.fatihbozik.shoppingcart.campaign.service.CampaignService;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartItemDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartService;
import io.github.fatihbozik.shoppingcart.cart.service.UpdateShoppingCartCommand;
import io.github.fatihbozik.shoppingcart.category.service.CategoryDetail;
import io.github.fatihbozik.shoppingcart.discount.calculator.DiscountCalculator;
import lombok.RequiredArgsConstructor;
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
    private final CampaignService campaignService;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public void apply(ShoppingCartDetail shoppingCart) {
        if (CollectionUtils.isEmpty(shoppingCart.getItems())) {
            return;
        }
        shoppingCart.getItems().forEach(this::applyCampaignDiscount);
        shoppingCart.calculateAndSetTotalPrice();

        shoppingCartService.updateShoppingCart(new UpdateShoppingCartCommand(shoppingCart));
    }

    private void applyCampaignDiscount(ShoppingCartItemDetail shoppingCartItem) {
        final CategoryDetail category = shoppingCartItem.getProduct().getCategory();
        List<CampaignDetail> campaigns = campaignService.getCampaignsByCategoryId(category.getId());
        if (CollectionUtils.isEmpty(campaigns)) {
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
        final DiscountCalculator discountCalculator = campaign.getDiscountType().getCalculator();
        return discountCalculator.calculateDiscount(shoppingCartItem, campaign);
    }
}
