package dev.es.myasset.domain.shared;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record Money(
        BigDecimal money
) {
    public Money {
        if(money == null || money.compareTo(BigDecimal.ZERO) < 0) {
           throw new IllegalArgumentException("금액은 음수가 될 수 없습니다.");
        }
    }

    public Money add(Money amount) {
        if(amount == null || amount.compareTo(Money.zero()) == 0) {
            throw new IllegalArgumentException("입금액은 0보다 커야 합니다.");
        }

        return new Money(money.add(amount.money));
    }

    public Money subtract(Money amount) {
        if(amount == null || amount.compareTo(Money.zero()) == 0) {
            throw new IllegalArgumentException("출금액은 0보다 커야 합니다.");
        }

        if(this.money.compareTo(amount.money) < 0) {
            throw new IllegalArgumentException("출금액은 잔액보다 클 수 없습니다.");
        }

        return new Money(money.subtract(amount.money));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public Money multiply(BigDecimal param) {
        return new Money(money.multiply(param));
    }

    public int compareTo(Money comparedMoney) {
        return this.money.compareTo(comparedMoney.money);
    }
}
