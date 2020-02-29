package io.github.fatihbozik.shoppingcart.cart.model;

import io.github.fatihbozik.shoppingcart.product.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "shopping_cart_items")
public class ShoppingCartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "seq_sc_item_id", sequenceName = "seq_sc_item_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sc_item_id")
    @Column(name = "id", nullable = false)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private int quantity = 0;

    @Column(name = "unit_price")
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(name = "campaign_discount")
    private BigDecimal campaignDiscount = BigDecimal.ZERO;

    /**
     * sub_total_price = (quantity * unit_price)
     */
    @Column(name = "sub_total_price")
    private BigDecimal subTotalPrice = BigDecimal.ZERO;

    /**
     * total_price = sub_total_price - campaignDiscount
     */
    @Column(name = "total_price")
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
