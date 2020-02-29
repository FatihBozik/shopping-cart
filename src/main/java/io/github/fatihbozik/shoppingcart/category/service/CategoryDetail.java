package io.github.fatihbozik.shoppingcart.category.service;

import io.github.fatihbozik.shoppingcart.category.model.Category;

public class CategoryDetail {
    private final Long id;
    private final String title;

    public CategoryDetail(final Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
    }
}
