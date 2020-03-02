package io.github.fatihbozik.shoppingcart.cart.controller;

import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.deliverycost.DeliveryCostCalculator;
import io.github.fatihbozik.shoppingcart.discount.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static io.github.fatihbozik.shoppingcart.util.Constants.APPLICATION_JSON;
import static java.math.BigDecimal.valueOf;

/**
 * Serves the following API endpoints:
 *
 * <ul>
 * <li> GET  /rest/v10/shopping-carts/{shoppingCartId}/print</li>
 * </ul>
 */
@RestController
@RequestMapping("rest")
@RequiredArgsConstructor
public class ShoppingCartController {
    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartController.class);

    private final DiscountService discountService;
    private final DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(valueOf(2), valueOf(5), valueOf(2.99));

    @GetMapping(value = "v10/shopping-carts/{shoppingCartId}/print", produces = {APPLICATION_JSON})
    public ShoppingCartDetail printShoppingCart(@PathVariable Long shoppingCartId) {
        final ShoppingCartDetail shoppingCart = discountService.applyDiscounts(shoppingCartId);
        final BigDecimal deliveryCost = deliveryCostCalculator.calculateFor(shoppingCart);
        shoppingCart.setDeliveryCost(deliveryCost);
        LOG.info("shoppingCart::{}", shoppingCart);
        return shoppingCart;
    }
}
