package dev.es.myasset.domain.portfolio;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.shared.YearMthConverter;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_key", "target_year_month"}),
        name="user_monthly_summary"
)
public class UserMonthlySummary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long summaryId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_key", nullable = false)
    private User user;

    @Convert(converter = YearMthConverter.class)
    private YearMonth targetYearMonth;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="fixed_spent")
    )
    private Money fixedSpent;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="variable_spent")
    )
    private Money variableSpent;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="avg_daily_variable_spent")
    )
    private Money avgDailyVariableSpent;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="total_spent")
    )
    private Money totalSpent;

}
