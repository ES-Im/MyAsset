package dev.es.myasset.domain.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    private static Stream<Arguments> invalidMoneyParameters() {
        return Stream.of(
                Arguments.of(null, "와같이 null은 금액이 될 수 없다."),
                Arguments.of(new BigDecimal("-0.0001"), "와 같이 음수는 금액이 될 수 없다."),
                Arguments.of(new BigDecimal("-1"), "와 같이 음수는 금액이 될 수 없다.")
        );
    }

    @ParameterizedTest(name = "{index} ==> 금액이 ''{0}'' {1}")
    @MethodSource("invalidMoneyParameters")
    @DisplayName("Money 객체 생성 실패 테스트")
    void createMoney_fail(BigDecimal value, String description) {
        // when then
        assertThatThrownBy(() ->
                new Money(value)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> validMoneyParameters() {
        return Stream.of(
                Arguments.of(BigDecimal.ZERO, "와 같이 0은 금액이 될 수 있다"),
                Arguments.of(BigDecimal.ONE, "와 같이 양수는 금액이 될 수 있다"),
                Arguments.of(new BigDecimal("0.001"), "와 같이 양수는 금액이 될 수 있다")
        );
    }

    @ParameterizedTest(name = "{index} ==> 금액이 ''{0}'' {1}")
    @MethodSource("validMoneyParameters")
    @DisplayName("Money 객체 생성 성공 테스트")
    void createMoney_success(BigDecimal value, String description) {
        // when then
        assertThat(new Money(value));
    }

    private static Stream<Arguments> validAddMoneyParameters() {
        return Stream.of(
                Arguments.of(new Money(BigDecimal.ONE), "와 같이 양수는 add할 수 있다."),
                Arguments.of(new Money(new BigDecimal("0.001")),"와 같이 양수는 add할 수 있다.")
        );
    }

    @ParameterizedTest(name = "{index} ==> ''{0}'' {1}")
    @MethodSource("validAddMoneyParameters")
    @DisplayName("Money add() 성공 테스트")
    void addMoney_success(Money value, String description) {
        Money zero = Money.zero();

        // when then
        assertThat(zero.add(value)).isEqualTo(value);
    }

    private static Stream<Arguments> invalidAddMoneyParameters() {
        return Stream.of(
                Arguments.of(Money.zero(), "와 같이 0을 더할 순 없다."),
                Arguments.of(null, "을 더할 순 없다.")
        );
    }

    @ParameterizedTest(name = "{index} ==> ''{0}'' {1}")
    @MethodSource("invalidAddMoneyParameters")
    @DisplayName("Money add() 실패 테스트")
    void addMoney_fail(Money value, String description) {
        Money money = Money.zero();

        assertThatThrownBy(() ->
                money.add(value)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Money subtract() 성공 테스트 - 양수 값을 뺄 수 있으며, 결과같이 0이상이어야 한다.")
    void subtractMoney_success() {
        // given
        Money money = new Money(BigDecimal.ONE);
        Money positiveValue = new Money(BigDecimal.ONE);

        // when then
        assertThat(money.subtract(positiveValue)).isEqualTo(Money.zero());
    }

    private static Stream<Arguments> invalidSubtractMoneyParameters() {
        return Stream.of(
                Arguments.of(Money.zero(), "0을 뺄 순 없다."),
                Arguments.of(null, "null을 뺄 순 없다."),
                Arguments.of(new Money(BigDecimal.ONE), "결과가 음수가 나올 수 없다.")
        );
    }

    @ParameterizedTest(name = "{index} ==> {1}")
    @MethodSource("invalidSubtractMoneyParameters")
    @DisplayName("Money subtract() 실패 테스트")
    void subtractMoney_fail(Money value, String description) {
        Money zero = Money.zero();

        assertThatThrownBy(() ->
                zero.subtract(value)
        ).isInstanceOf(IllegalArgumentException.class);

    }

    private static Stream<Arguments> compareToValues() {
        return Stream.of(
                Arguments.of(new Money(BigDecimal.ONE), 1, "더 작은 금액과 비교"),
                Arguments.of(new Money(BigDecimal.TWO), 0, "동일한 금액과 비교"),
                Arguments.of(new Money(BigDecimal.TEN), -1, "더 큰 금액과 비교")
        );
    }

    @ParameterizedTest(name = "{2}시 결과는 {1}이다.")
    @MethodSource("compareToValues")
    @DisplayName("Money compareTo overriding 테스트")
    void compareToTest(Money value, int result, String description) {
        Money two = new Money(BigDecimal.TWO);

        assertThat(two.compareTo(value)).isEqualTo(result);
    }



}