package io.github.fatihbozik.shoppingcart.coupon.model;

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
@Table(name = "coupons")
public class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "seq_coupon_id", sequenceName = "seq_coupon_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_coupon_id")
    @Column(name = "id", nullable = false)
    @Id
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "min_purchase_amount")
    private BigDecimal minPurchaseAmount;

    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CouponStatus status;
}
