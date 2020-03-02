package io.github.fatihbozik.shoppingcart.discount.service;

import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartService;
import io.github.fatihbozik.shoppingcart.discount.rule.DiscountRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final List<DiscountRule> discountRules;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public ShoppingCartDetail applyDiscounts(Long shoppingCartId) {
        final ShoppingCartDetail shoppingCart = shoppingCartService.getShoppingCartById(shoppingCartId);
        discountRules.forEach(discountRule -> discountRule.apply(shoppingCart));
        return shoppingCart;
    }
}
