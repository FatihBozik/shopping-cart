package io.github.fatihbozik.shoppingcart.campaign.service;

import io.github.fatihbozik.shoppingcart.campaign.model.Campaign;
import io.github.fatihbozik.shoppingcart.campaign.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CampaignDetail> getCampaignsByCategoryId(Long categoryId) {
        List<Campaign> campaigns = campaignRepository.findCampaignsByCategoryId(categoryId);
        return campaigns.stream().map(CampaignDetail::new).collect(Collectors.toList());
    }
}
