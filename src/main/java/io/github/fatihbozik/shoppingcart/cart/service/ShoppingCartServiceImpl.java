package io.github.fatihbozik.shoppingcart.cart.service;

import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.repository.ShoppingCartRepository;
import io.github.fatihbozik.shoppingcart.discount.rule.DiscountRule;
import io.github.fatihbozik.shoppingcart.fault.ServiceRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.fatihbozik.shoppingcart.util.Errors.SHOPPING_CART_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final List<DiscountRule> discountRules;

    @Override
    public ShoppingCartDetail getShoppingCartById(Long shoppingCartId) {
        return shoppingCartRepository.findShoppingCartById(shoppingCartId)
                .map(ShoppingCartDetail::new)
                .orElseThrow(() -> new ServiceRuntimeException(SHOPPING_CART_NOT_FOUND));
    }

    @Override
    public ShoppingCartDetail updateShoppingCart(UpdateShoppingCartCommand updateShoppingCartCommand) {
        final ShoppingCart shoppingCart = shoppingCartRepository.getOne(updateShoppingCartCommand.getId());
        shoppingCart.setTotalPrice(updateShoppingCartCommand.getTotalPrice());
        shoppingCart.getItems().clear();
        shoppingCart.getItems().addAll(ShoppingCartItemDetail.toShoppingCartItems(shoppingCart, updateShoppingCartCommand.getShoppingCartItems()));
        return new ShoppingCartDetail(shoppingCartRepository.saveAndFlush(shoppingCart));
    }

    @Override
    public void applyDiscounts(Long shoppingCartId) {
        ShoppingCartDetail shoppingCart = getShoppingCartById(shoppingCartId);
        discountRules.forEach(discountRule -> discountRule.apply(shoppingCart));
    }
}
