package io.github.fatihbozik.shoppingcart.discount.rule;

import com.google.common.collect.Sets;
import io.github.fatihbozik.shoppingcart.campaign.model.Campaign;
import io.github.fatihbozik.shoppingcart.campaign.service.CampaignDetail;
import io.github.fatihbozik.shoppingcart.campaign.service.CampaignService;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.cart.service.ApplyCampaignRequest;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartService;
import io.github.fatihbozik.shoppingcart.cart.service.UpdateShoppingCartItemRequest;
import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignDiscountRuleTest {
    @InjectMocks
    private CampaignDiscountRule campaignDiscountRule;

    @Mock
    private CampaignService campaignService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Test
    void shouldNotAnythingIfShoppingCartItemsAreEmpty() {
        final ShoppingCart emptyShoppingCart = new ShoppingCart();
        emptyShoppingCart.setId(1L);
        emptyShoppingCart.setTotalPrice(BigDecimal.ZERO);
        emptyShoppingCart.setItems(Collections.emptySet());
        final ShoppingCartDetail emptyShoppingCartDetail = new ShoppingCartDetail(emptyShoppingCart);

        campaignDiscountRule.apply(emptyShoppingCartDetail);

        verifyNoInteractions(campaignService);
        verifyNoInteractions(shoppingCartService);
    }

    @Test
    void shouldNotApplyCampaignIfNoCampaignMatches() {
        final Category computersCategory = new Category();
        computersCategory.setId(1L);
        computersCategory.setTitle("Computers");

        final Product macBookPro = new Product();
        macBookPro.setId(1L);
        macBookPro.setTitle("New MacBook Pro 16 inch");
        macBookPro.setCategory(computersCategory);

        final ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(1L);
        shoppingCartItem.setUnitPrice(BigDecimal.valueOf(5));
        shoppingCartItem.setProduct(macBookPro);
        shoppingCartItem.setUnitPrice(BigDecimal.valueOf(15_000L));
        shoppingCartItem.setQuantity(1);
        shoppingCartItem.setTotalPrice(BigDecimal.valueOf(15_000L));

        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(Sets.newHashSet(shoppingCartItem));
        shoppingCart.setTotalPrice(BigDecimal.valueOf(15_000L));
        when(campaignService.getCampaignsByCategoryId(1L)).thenReturn(null);

        campaignDiscountRule.apply(new ShoppingCartDetail(shoppingCart));

        final ArgumentCaptor<ApplyCampaignRequest> argumentCaptor = ArgumentCaptor.forClass(ApplyCampaignRequest.class);
        verify(shoppingCartService).applyCampaign(argumentCaptor.capture());

        final ApplyCampaignRequest updateShoppingCartCommand = argumentCaptor.getValue();
        assertThat(updateShoppingCartCommand.getId(), is(1L));
        assertThat(updateShoppingCartCommand.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(15_000L)));
    }

    @Test
    void shouldApplyMaximumAmountOfCampaignDiscountToShoppingCartSuccessfully() {
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

        final Campaign campaign2 = new Campaign();
        campaign2.setId(2L);
        campaign2.setTitle("50% Discount for Computers category if bought more than 5 items");
        campaign2.setCategory(computersCategory);
        campaign2.setDiscountType(DiscountType.RATE);
        campaign2.setDiscountValue(BigDecimal.valueOf(50));
        campaign2.setItemThreshold(5);

        final Campaign campaign3 = new Campaign();
        campaign3.setId(3L);
        campaign3.setTitle("5 TL Discount for Computers category if bought more than 5 items");
        campaign3.setCategory(computersCategory);
        campaign3.setDiscountType(DiscountType.AMOUNT);
        campaign3.setDiscountValue(BigDecimal.valueOf(5));
        campaign3.setItemThreshold(5);

        final Product macBookPro = new Product();
        macBookPro.setId(1L);
        macBookPro.setTitle("New MacBook Pro 16 inch");
        macBookPro.setCategory(computersCategory);

        final ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(1L);
        shoppingCartItem.setUnitPrice(BigDecimal.valueOf(5));
        shoppingCartItem.setProduct(macBookPro);
        shoppingCartItem.setUnitPrice(BigDecimal.valueOf(15_000L));
        shoppingCartItem.setQuantity(7);
        shoppingCartItem.setSubTotalPrice(BigDecimal.valueOf(105_000L));
        shoppingCartItem.setTotalPrice(BigDecimal.valueOf(105_000L));

        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setItems(Sets.newHashSet(shoppingCartItem));
        shoppingCart.setCouponDiscount(BigDecimal.ZERO);
        shoppingCart.setTotalPrice(BigDecimal.valueOf(105_000L));
        CampaignDetail campaignDetail1 = new CampaignDetail(campaign1);
        CampaignDetail campaignDetail2 = new CampaignDetail(campaign2);
        CampaignDetail campaignDetail3 = new CampaignDetail(campaign3);
        shoppingCartItem.setShoppingCart(shoppingCart);
        when(campaignService.getCampaignsByCategoryId(1L))
                .thenReturn(asList(campaignDetail1, campaignDetail2, campaignDetail3));

        campaignDiscountRule.apply(new ShoppingCartDetail(shoppingCart));

        final ArgumentCaptor<ApplyCampaignRequest> argumentCaptor = ArgumentCaptor.forClass(ApplyCampaignRequest.class);
        verify(shoppingCartService).applyCampaign(argumentCaptor.capture());

        final ApplyCampaignRequest updateShoppingCartCommand = argumentCaptor.getValue();
        assertThat(updateShoppingCartCommand.getId(), is(1L));
        assertThat(getCampaignDiscount(updateShoppingCartCommand), Matchers.comparesEqualTo(BigDecimal.valueOf(52_500L)));
        assertThat(updateShoppingCartCommand.getTotalPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(52_500L)));
    }

    private BigDecimal getCampaignDiscount(ApplyCampaignRequest updateShoppingCartCommand) {
        return updateShoppingCartCommand.getUpdateShoppingCartItemRequests().stream()
                .map(UpdateShoppingCartItemRequest::getCampaignDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
