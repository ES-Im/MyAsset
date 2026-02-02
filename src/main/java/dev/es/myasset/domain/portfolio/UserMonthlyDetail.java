package dev.es.myasset.domain.portfolio;

import dev.es.myasset.domain.shared.ExpenseType;
import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.Money;
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
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"summary_id", "expense_type"}),
        name="user_monthly_detail"
)
public class UserMonthlyDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long detailId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="summary_id", nullable = false)
    private UserMonthlySummary userMonthlySummary;

    @Enumerated(STRING)
    private ExpenseType expenseType;

    @Embedded
    @AttributeOverride(
            name="money"
            , column = @Column(name = "avg_spent")
    )
    private Money avgSpent;

    @Embedded
    @AttributeOverride(
            name="money"
            , column = @Column(name = "total_spent")
    )
    private Money totalSpent;

}
