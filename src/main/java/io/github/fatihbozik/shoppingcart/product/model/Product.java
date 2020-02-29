package io.github.fatihbozik.shoppingcart.product.model;

import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.common.model.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "seq_product_id", sequenceName = "seq_product_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_product_id")
    @Column(name = "id", nullable = false)
    @Id
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Embedded
    private Price price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
