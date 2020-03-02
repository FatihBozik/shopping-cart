package io.github.fatihbozik.shoppingcart.deliverycost;

import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCartItem;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


@RequiredArgsConstructor
public class DeliveryCostCalculator {
    private final BigDecimal costPerDelivery;
    private final BigDecimal costPerProduct;
    private final BigDecimal fixedCost;

    public BigDecimal calculateFor(ShoppingCart shoppingCart) {
        final BigDecimal numberOfDeliveries = findNumberOfDistinctCategories(shoppingCart);
        final BigDecimal deliveryCost = costPerDelivery.multiply(numberOfDeliveries);

        final BigDecimal numberOfProducts = findNumberOfDifferentProducts(shoppingCart);
        final BigDecimal productCost = costPerProduct.multiply(numberOfProducts);

        return deliveryCost.add(productCost).add(fixedCost);
    }

    private BigDecimal findNumberOfDistinctCategories(ShoppingCart shoppingCart) {
        long numberOfDistinctCategories = shoppingCart.getItems().stream()
                .map(ShoppingCartItem::getProduct)
                .map(Product::getCategory)
                .distinct()
                .count();
        return BigDecimal.valueOf(numberOfDistinctCategories);
    }

    private BigDecimal findNumberOfDifferentProducts(ShoppingCart shoppingCart) {
        long numberOfDistinctProducts = shoppingCart.getItems().stream()
                .map(ShoppingCartItem::getProduct)
                .distinct()
                .count();
        return BigDecimal.valueOf(numberOfDistinctProducts);
    }
}
