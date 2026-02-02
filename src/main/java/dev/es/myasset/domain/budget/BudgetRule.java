package dev.es.myasset.domain.budget;

import dev.es.myasset.domain.shared.ExpenseType;
import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private ValueType valueType;    // default = 적금 20% / 고정비 50% / 변동비 30%

    private BigDecimal bgtParam;    // valueType = [AMOUNT : 절대기준값 / RATIO : 월소득액의 bgtParam %]


    public static BudgetRule createRule(
            User user, ExpenseType expenseType, ValueType valueType, BigDecimal bgtParam
    ) {
        BudgetRule budgetRule = new BudgetRule();

        budgetRule.user = requireNonNull(user);
        budgetRule.expenseType = requireNonNull(expenseType);
        budgetRule.valueType = requireNonNull(valueType);
        budgetRule.bgtParam = requireNonNull(bgtParam);

        state(bgtParam.compareTo(BigDecimal.ZERO) > 0, "매개값은 양수여야 합니다");

        return budgetRule;
    }

    public Money calculateBudgetLimit(Money monthlyIncome) {
        if(this.valueType.equals(AMOUNT)) return new Money(this.bgtParam);

        if(this.valueType.equals(RATIO)) return monthlyIncome.multiply(bgtParam);

        throw new IllegalStateException("지원하지 않는 예산 설정(BudgetRule - valueType");
    }

}
