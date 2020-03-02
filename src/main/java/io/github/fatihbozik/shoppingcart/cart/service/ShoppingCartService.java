package io.github.fatihbozik.shoppingcart.cart.service;

public interface ShoppingCartService {
    ShoppingCartDetail getShoppingCartById(Long shoppingCartId);

    ShoppingCartDetail applyCampaign(ApplyCampaignRequest applyCampaignRequest);

    ShoppingCartDetail applyCoupon(ApplyCouponRequest applyCouponRequest);
}
