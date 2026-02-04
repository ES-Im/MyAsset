package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.YearMthConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.Objects.requireNonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
    uniqueConstraints  = {@UniqueConstraint(columnNames = {"card_id", "billing_mth"})},
    name = "card_mth"
)
public class CardMonth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long cardMthId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="card_id", nullable = false)
    private Card card;

    @Convert(converter = YearMthConverter.class)
    @Column(nullable = false)
    private YearMonth billingMth;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="mth_used_amt", nullable = false)
    )
    private Money mthUsedAmt;

    public static CardMonth createNewCardMonth(Card card, LocalDate now) {
        requireNonNull(card);
        requireNonNull(now);

        CardMonth cardMonth = new CardMonth();

        cardMonth.card = card;
        cardMonth.billingMth = YearMonth.from(now);
        cardMonth.mthUsedAmt = Money.zero();

        return cardMonth;
    }


    public void addUsedMthAmt(Money money) {
        this.mthUsedAmt = this.mthUsedAmt.add(money);
    }
}
