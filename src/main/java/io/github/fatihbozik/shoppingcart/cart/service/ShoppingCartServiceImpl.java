package io.github.fatihbozik.shoppingcart.cart.service;

import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.cart.repository.ShoppingCartRepository;
import io.github.fatihbozik.shoppingcart.discount.rule.DiscountRule;
import io.github.fatihbozik.shoppingcart.fault.ServiceRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static io.github.fatihbozik.shoppingcart.util.Errors.SHOPPING_CART_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final List<DiscountRule> discountRules;

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDetail getShoppingCartById(Long shoppingCartId) {
        return shoppingCartRepository.findShoppingCartById(shoppingCartId)
                .map(ShoppingCartDetail::new)
                .orElseThrow(() -> new ServiceRuntimeException(SHOPPING_CART_NOT_FOUND));
    }

    @Override
    @Transactional
    public ShoppingCartDetail updateShoppingCart(UpdateShoppingCartCommand updateShoppingCartCommand) {
        final ShoppingCart shoppingCart = shoppingCartRepository.getOne(updateShoppingCartCommand.getId());
        updateShoppingCartItems(updateShoppingCartCommand, shoppingCart);
        shoppingCart.setTotalPrice(updateShoppingCartCommand.getTotalPrice());
        return new ShoppingCartDetail(shoppingCartRepository.saveAndFlush(shoppingCart));
    }

    @Override
    @Transactional
    public void applyDiscounts(Long shoppingCartId) {
        ShoppingCartDetail shoppingCart = getShoppingCartById(shoppingCartId);
        discountRules.forEach(discountRule -> discountRule.apply(shoppingCart));
    }


    private void updateShoppingCartItems(UpdateShoppingCartCommand updateShoppingCartCommand, ShoppingCart shoppingCart) {
        Set<UpdateShoppingCartItemCommand> updateShoppingCartItemCommands = updateShoppingCartCommand.getUpdateShoppingCartItemCommands();
        shoppingCart.getItems().forEach(shoppingCartItem -> updateShoppingCartItem(shoppingCartItem, updateShoppingCartItemCommands));
    }

    private void updateShoppingCartItem(ShoppingCartItem shoppingCartItem, Set<UpdateShoppingCartItemCommand> updateShoppingCartItemCommands) {
        updateShoppingCartItemCommands.stream()
                .filter(updateShoppingCartItemCommand -> shoppingCartItem.getId().equals(updateShoppingCartItemCommand.getId()))
                .findFirst()
                .ifPresent(updateShoppingCartItemCommand -> {
                    shoppingCartItem.setCampaignDiscount(updateShoppingCartItemCommand.getCampaignDiscount());
                    shoppingCartItem.setTotalPrice(updateShoppingCartItemCommand.getTotalPrice());
                });
    }
}
