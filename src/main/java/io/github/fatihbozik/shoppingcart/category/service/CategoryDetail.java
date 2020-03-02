package io.github.fatihbozik.shoppingcart.category.service;

import io.github.fatihbozik.shoppingcart.category.model.Category;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
public class CategoryDetail {
    private final Long id;
    private final String title;
    private final Long parentCategoryId;

    public CategoryDetail(final Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.parentCategoryId = Optional.ofNullable(category.getParent()).map(Category::getId).orElse(null);
    }
}
