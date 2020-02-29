package io.github.fatihbozik.shoppingcart.common.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Price implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "price_value", nullable = false)
    private BigDecimal value;

    @Column(name = "price_unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private PriceUnit unit;
}
