package io.github.fatihbozik.shoppingcart.campaign.service;

import io.github.fatihbozik.shoppingcart.campaign.model.Campaign;
import io.github.fatihbozik.shoppingcart.campaign.repository.CampaignRepository;
import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaignServiceImplTest {
    @InjectMocks
    private CampaignServiceImpl campaignService;

    @Mock
    private CampaignRepository campaignRepository;

    @Test
    void shouldGetCampaignsByCategoryId() {
        final Category computersCategory = new Category();
        computersCategory.setId(1L);
        computersCategory.setTitle("Computers");

        final Campaign campaign1 = new Campaign();
        campaign1.setId(1L);
        campaign1.setTitle("20% Discount for Computers category if bought more than 3 items");
        campaign1.setCategory(computersCategory);
        campaign1.setDiscountType(DiscountType.RATE);
        campaign1.setDiscountValue(BigDecimal.valueOf(20));
        campaign1.setItemThreshold(3);

        when(campaignRepository.findCampaignsByCategoryId(1L)).thenReturn(Collections.singletonList(campaign1));

        List<CampaignDetail> campaigns = campaignService.getCampaignsByCategoryId(1L);
        CampaignDetail campaignDetail = campaigns.get(0);
        assertThat(campaigns.size(), is(1));
        assertThat(campaignDetail.getId(), is(1L));
        assertThat(campaignDetail.getTitle(), is("20% Discount for Computers category if bought more than 3 items"));
        assertThat(campaignDetail.getDiscountType(), is(DiscountType.RATE));
        assertThat(campaignDetail.getDiscountValue(), Matchers.comparesEqualTo(BigDecimal.valueOf(20)));
        assertThat(campaignDetail.getItemThreshold(), is(3));
    }

}
