package dev.es.myasset.domain.budget;

import dev.es.myasset.domain.category.ExpenseType;
import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "budget_rule")
public class BudgetRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long bgtRuleId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_key", nullable = true)
    private User user;

    @Enumerated(STRING)
    private ExpenseType expenseType;

    @Enumerated(STRING)
    private ValueType valueType;

    private Double bgtParam;

}
