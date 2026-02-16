package dev.es.myasset.domain.budget;

import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static dev.es.myasset.domain.budget.BudgetRule.createRule;
import static dev.es.myasset.domain.budget.ValueType.AMOUNT;
import static dev.es.myasset.domain.budget.ValueType.RATIO;
import static dev.es.myasset.domain.common.UserFixture.createUser;
import static dev.es.myasset.domain.shared.ExpenseType.FIXED;
import static org.assertj.core.api.Assertions.*;

class BudgetRuleTest {

    @Test
    @DisplayName("예산 규칙 생성 시, 파라미터 값은 음수나 0으로 설정할 수 없다.")
    void cantCreateRuleTest() {
        // given
        User user = createUser();
        BigDecimal zeroParam = BigDecimal.ZERO;
        BigDecimal negativeParam = BigDecimal.valueOf(-1);


        // when, then
        assertThatThrownBy(()->
                createRule(user, FIXED, AMOUNT, zeroParam)
        ).isInstanceOf(IllegalStateException.class);

        assertThatThrownBy(()->
                createRule(user, FIXED, AMOUNT, negativeParam)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("예산 규칙 생성 시, 파라미터 값은 양수로 설정한다..")
    void createRuleTest() {
        // given
        User user = createUser();
        BigDecimal oneParam = BigDecimal.ONE;

        // when, then
        createRule(user, FIXED, AMOUNT, oneParam);
    }

    private static Stream<Arguments> happyBgyRuleCases() {
        Money monthlyIncome = new Money(BigDecimal.valueOf(1_000_000L));
        BigDecimal amountParam = BigDecimal.valueOf(1_000_000L);
        BigDecimal ratioParam = BigDecimal.valueOf(1);

        return Stream.of(
                Arguments.of(monthlyIncome, AMOUNT, "월소득액과 동일하게 설정", amountParam, new Money(amountParam)),
                Arguments.of(monthlyIncome, RATIO, "비율을 100%로 설정", ratioParam, monthlyIncome.multiply(ratioParam))
        );
    }

    @ParameterizedTest(name="{index} ==> ''{1}'' 계산법 시, ''{2}''하거나 그 이하로 설정할 수 있다.")
    @DisplayName("예산 커스텀 설정 해피 케이스")
    @MethodSource("happyBgyRuleCases")
    void calculateBudgetLimit_success(Money monthlyIncome, ValueType valueType, String description, BigDecimal bgyParam, Money expectedLimit) {
        // given
        User user = createUser();
        BudgetRule rules = createRule(user, FIXED, valueType, bgyParam);
//        System.out.println(rules);

        // when then
        assertThat(rules.calculateBudgetLimit(monthlyIncome))
                .isEqualTo(expectedLimit);
    }

    private static Stream<Arguments> exceptionBgyRuleCases() {
        Money monthlyIncome = new Money(BigDecimal.valueOf(1_000_000L));

        return Stream.of(
                Arguments.of(monthlyIncome, AMOUNT, "월소득액보다 크게 설정",BigDecimal.valueOf(1_000_001L)),
                Arguments.of(monthlyIncome, RATIO, "비율을 100%를 초과하여 설정", BigDecimal.valueOf(1.1))
        );
    }

    @ParameterizedTest(name="{index} ==> ''{1}'' 계산법 시, ''{2}''할 수 없다.")
    @DisplayName("예산 커스텀 설정 실패 케이스")
    @MethodSource("exceptionBgyRuleCases")
    void calculateBudgetLimit_fail(Money monthlyIncome, ValueType valueType, String description, BigDecimal bgyParam) {
        // given
        User user = createUser();
        BudgetRule rules = createRule(user, FIXED, valueType, bgyParam);
//        System.out.println(rules);

        // when then
         assertThatThrownBy(() ->
                 rules.calculateBudgetLimit(monthlyIncome)
         ).isInstanceOf(IllegalStateException.class);
    }

}