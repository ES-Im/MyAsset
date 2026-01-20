package dev.es.myasset.domain.budget;

import dev.es.myasset.domain.category.Category;
import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "budget")
public class Budget extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key", nullable = false)
    private User user;

    private Integer year;

    private Integer month;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cgy_id", nullable = true)
    private Category category;

    private BigDecimal amt;

}
