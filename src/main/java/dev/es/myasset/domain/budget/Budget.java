package dev.es.myasset.domain.budget;

import dev.es.myasset.domain.shared.ExpenseType;
import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.shared.YearMthConverter;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

import static jakarta.persistence.EnumType.STRING;

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

    @Convert(converter = YearMthConverter.class)
    private YearMonth targetYearMonth;

    @Enumerated(STRING)
    private ExpenseType expenseType;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="amt")
    )
    private Money amt;

    @Embedded
    private Money monthlyIncome;

}
