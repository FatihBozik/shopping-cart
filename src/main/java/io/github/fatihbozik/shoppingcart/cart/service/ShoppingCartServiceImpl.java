package io.github.fatihbozik.shoppingcart.cart.service;

import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.cart.repository.ShoppingCartRepository;
import io.github.fatihbozik.shoppingcart.fault.ServiceRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static io.github.fatihbozik.shoppingcart.util.Errors.SHOPPING_CART_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDetail getShoppingCartById(Long shoppingCartId) {
        return shoppingCartRepository.findShoppingCartById(shoppingCartId)
                .map(ShoppingCartDetail::new)
                .orElseThrow(() -> new ServiceRuntimeException(SHOPPING_CART_NOT_FOUND));
    }

    @Override
    @Transactional
    public ShoppingCartDetail applyCampaign(ApplyCampaignRequest updateShoppingCartCommand) {
        final ShoppingCart shoppingCart = shoppingCartRepository.getOne(updateShoppingCartCommand.getId());
        updateShoppingCartItems(updateShoppingCartCommand, shoppingCart);
        shoppingCart.setTotalPrice(updateShoppingCartCommand.getTotalPrice());
        return new ShoppingCartDetail(shoppingCartRepository.saveAndFlush(shoppingCart));
    }

    @Override
    public ShoppingCartDetail applyCoupon(ApplyCouponRequest applyCouponRequest) {
        final ShoppingCart shoppingCart = shoppingCartRepository.getOne(applyCouponRequest.getShoppingCartId());
        shoppingCart.setCouponDiscount(applyCouponRequest.getCouponDiscount());
        shoppingCart.setTotalPrice(applyCouponRequest.getTotalPrice());
        return new ShoppingCartDetail(shoppingCartRepository.saveAndFlush(shoppingCart));
    }

    private void updateShoppingCartItems(ApplyCampaignRequest updateShoppingCartCommand, ShoppingCart shoppingCart) {
        Set<UpdateShoppingCartItemRequest> updateShoppingCartItemRequests = updateShoppingCartCommand.getUpdateShoppingCartItemRequests();
        shoppingCart.getItems().forEach(shoppingCartItem -> updateShoppingCartItem(shoppingCartItem, updateShoppingCartItemRequests));
    }

    private void updateShoppingCartItem(ShoppingCartItem shoppingCartItem, Set<UpdateShoppingCartItemRequest> updateShoppingCartItemRequests) {
        updateShoppingCartItemRequests.stream()
                .filter(updateShoppingCartItemRequest -> shoppingCartItem.getId().equals(updateShoppingCartItemRequest.getId()))
                .findFirst()
                .ifPresent(updateShoppingCartItemRequest -> {
                    shoppingCartItem.setCampaignDiscount(updateShoppingCartItemRequest.getCampaignDiscount());
                    shoppingCartItem.setTotalPrice(updateShoppingCartItemRequest.getTotalPrice());
                });
    }
}
