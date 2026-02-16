package dev.es.myasset.domain.budget;

import dev.es.myasset.domain.shared.ExpenseType;
import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

import static dev.es.myasset.domain.budget.ValueType.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "budget_rule")
@ToString(exclude = {"user_key"})
public class BudgetRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long bgtRuleId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_key", nullable = true)
    private User user;

    @Enumerated(STRING)
    @Column(nullable = false)
    private ExpenseType expenseType;

    @Enumerated(STRING)
    @Column(nullable = false)
    private ValueType valueType;    // default = 적금 20% / 고정비 50% / 변동비 30%

    @Column(nullable = false)
    private BigDecimal bgtParam;    // valueType = [AMOUNT : 절대기준값 / RATIO : 월소득액의 bgtParam %]

    public static BudgetRule createRule(
            User user, ExpenseType expenseType, ValueType valueType, BigDecimal bgtParam
    ) {
        state(bgtParam.compareTo(BigDecimal.ZERO) > 0, "매개값은 음수가 될 수 없습니다.");

        BudgetRule budgetRule = new BudgetRule();

        budgetRule.user = requireNonNull(user);
        budgetRule.expenseType = requireNonNull(expenseType);
        budgetRule.valueType = requireNonNull(valueType);
        budgetRule.bgtParam = requireNonNull(bgtParam);

        return budgetRule;
    }

    public Money calculateBudgetLimit(Money monthlyIncome) {
        requireNonNull(monthlyIncome);

        if(this.valueType.equals(AMOUNT)) {
            Money limit = new Money(this.bgtParam);

            state(monthlyIncome.compareTo(limit) >= 0
                    , "예산 설정 시, 월소득액을 초과할 수 없습니다.");

            return limit;
        }

        if(this.valueType.equals(RATIO)) {

            state(this.bgtParam.compareTo(BigDecimal.ONE) <= 0
                    , "예산 설정 시, 비율은 1(100%)를 초과할 수 없습니다.");

            return monthlyIncome.multiply(bgtParam);

        }

        throw new IllegalStateException("지원하지 않는 예산 설정(BudgetRule - valueType");
    }

}
