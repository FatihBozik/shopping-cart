package io.github.fatihbozik.shoppingcart.campaign.service;

import io.github.fatihbozik.shoppingcart.campaign.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;
}
