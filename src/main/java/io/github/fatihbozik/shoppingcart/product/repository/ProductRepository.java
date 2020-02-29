package io.github.fatihbozik.shoppingcart.product.repository;

import io.github.fatihbozik.shoppingcart.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
