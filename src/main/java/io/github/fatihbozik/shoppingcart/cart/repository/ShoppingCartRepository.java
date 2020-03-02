package io.github.fatihbozik.shoppingcart.cart.repository;

import io.github.fatihbozik.shoppingcart.cart.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findShoppingCartById(Long id);
}
