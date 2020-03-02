package io.github.fatihbozik.shoppingcart.campaign.service;

import java.util.List;

public interface CampaignService {
    List<CampaignDetail> getCampaignsByCategoryId(Long categoryId);
}
