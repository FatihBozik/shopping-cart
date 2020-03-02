package io.github.fatihbozik.shoppingcart.category.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "categories")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "seq_category_id", sequenceName = "seq_category_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_category_id")
    @Column(name = "id", nullable = false)
    @Id
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Category> children = new HashSet<>(0);
}
