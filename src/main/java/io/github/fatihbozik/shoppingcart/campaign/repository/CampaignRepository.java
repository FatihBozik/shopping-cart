package io.github.fatihbozik.shoppingcart.campaign.repository;

import io.github.fatihbozik.shoppingcart.campaign.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
