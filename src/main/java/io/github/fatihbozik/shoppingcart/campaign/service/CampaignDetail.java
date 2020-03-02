package io.github.fatihbozik.shoppingcart.campaign.service;

import io.github.fatihbozik.shoppingcart.campaign.model.Campaign;
import io.github.fatihbozik.shoppingcart.category.service.CategoryDetail;
import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CampaignDetail {
    private final Long id;
    private final String title;
    private final CategoryDetail category;
    private final BigDecimal discountValue;
    private final DiscountType discountType;
    private final Integer itemThreshold;

    public CampaignDetail(final Campaign campaign) {
        this.id = campaign.getId();
        this.title = campaign.getTitle();
        this.category = new CategoryDetail(campaign.getCategory());
        this.discountValue = campaign.getDiscountValue();
        this.discountType = campaign.getDiscountType();
        this.itemThreshold = campaign.getItemThreshold();
    }
}
