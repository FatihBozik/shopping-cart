package io.github.fatihbozik.shoppingcart.cart.repository;

import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
