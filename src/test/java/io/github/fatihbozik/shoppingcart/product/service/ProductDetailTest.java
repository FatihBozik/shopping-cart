package io.github.fatihbozik.shoppingcart.product.service;

import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.category.service.CategoryDetail;
import io.github.fatihbozik.shoppingcart.product.model.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ProductDetailTest {

    @Test
    void shouldConvertProductToDetailSuccessfully() {
        final Category category = new Category();
        category.setId(1L);
        category.setTitle("Computers");

        final Product product = new Product();
        product.setId(1L);
        product.setTitle("MacBook Pro");
        product.setPrice(BigDecimal.valueOf(10));
        product.setCategory(category);
        ProductDetail productDetail = new ProductDetail(product);

        assertThat(productDetail.getId(), is(product.getId()));
        assertThat(productDetail.getTitle(), is(product.getTitle()));
        assertThat(productDetail.getPrice(), Matchers.comparesEqualTo(BigDecimal.valueOf(10)));
        final CategoryDetail categoryDetail = productDetail.getCategory();
        assertThat(categoryDetail.getId(), is(category.getId()));
        assertThat(categoryDetail.getTitle(), is(category.getTitle()));
    }
}
