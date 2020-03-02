package io.github.fatihbozik.shoppingcart.cart.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "seq_sc_id", sequenceName = "seq_sc_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sc_id")
    @Column(name = "id", nullable = false)
    @Id
    private Long id;

    @JoinColumn(name = "shopping_cart_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShoppingCartItem> items = new HashSet<>(0);

    @Column(name = "coupon_discount", nullable = false)
    private BigDecimal couponDiscount = BigDecimal.ZERO;

    @Column(name = "delivery_cost", nullable = false)
    private BigDecimal deliveryCost = BigDecimal.ZERO;

    /**
     * total_price = [(total of items' total_price) - coupon_discount] + delivery_cost
     */
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
