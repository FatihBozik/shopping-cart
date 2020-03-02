package io.github.fatihbozik.shoppingcart.campaign.model;

import io.github.fatihbozik.shoppingcart.category.model.Category;
import io.github.fatihbozik.shoppingcart.common.model.DiscountType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "campaigns")
public class Campaign implements Serializable {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "seq_campaign_id", sequenceName = "seq_campaign_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_campaign_id")
    @Column(name = "id", nullable = false)
    @Id
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "item_threshold", nullable = false)
    private Integer itemThreshold;
}
