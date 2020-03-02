package io.github.fatihbozik.shoppingcart.deliverycost;

import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartDetail;
import io.github.fatihbozik.shoppingcart.cart.service.ShoppingCartItemDetail;
import io.github.fatihbozik.shoppingcart.category.service.CategoryDetail;
import io.github.fatihbozik.shoppingcart.product.service.ProductDetail;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


@RequiredArgsConstructor
public class DeliveryCostCalculator {
    private final BigDecimal costPerDelivery;
    private final BigDecimal costPerProduct;
    private final BigDecimal fixedCost;

    public BigDecimal calculateFor(ShoppingCartDetail shoppingCart) {
        final BigDecimal numberOfDeliveries = findNumberOfDistinctCategories(shoppingCart);
        final BigDecimal deliveryCost = costPerDelivery.multiply(numberOfDeliveries);

        final BigDecimal numberOfProducts = findNumberOfDifferentProducts(shoppingCart);
        final BigDecimal productCost = costPerProduct.multiply(numberOfProducts);

        return deliveryCost.add(productCost).add(fixedCost);
    }

    private BigDecimal findNumberOfDistinctCategories(ShoppingCartDetail shoppingCart) {
        long numberOfDistinctCategories = shoppingCart.getItems().stream()
                .map(ShoppingCartItemDetail::getProduct)
                .map(ProductDetail::getCategory)
                .map(CategoryDetail::getId)
                .distinct()
                .count();
        return BigDecimal.valueOf(numberOfDistinctCategories);
    }

    private BigDecimal findNumberOfDifferentProducts(ShoppingCartDetail shoppingCart) {
        long numberOfDistinctProducts = shoppingCart.getItems().stream()
                .map(ShoppingCartItemDetail::getProduct)
                .map(ProductDetail::getId)
                .distinct()
                .count();
        return BigDecimal.valueOf(numberOfDistinctProducts);
    }
}
