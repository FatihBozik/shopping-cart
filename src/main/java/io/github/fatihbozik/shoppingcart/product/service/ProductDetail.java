package io.github.fatihbozik.shoppingcart.product.service;

import io.github.fatihbozik.shoppingcart.category.service.CategoryDetail;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class ProductDetail {
    private final Long id;
    private final String title;
    private final BigDecimal price;
    private final CategoryDetail category;

    public ProductDetail(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.category = new CategoryDetail(product.getCategory());
    }
}
