package io.github.fatihbozik.shoppingcart.category.repository;

import io.github.fatihbozik.shoppingcart.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
