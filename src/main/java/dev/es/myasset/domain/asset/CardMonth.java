package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.YearMthConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
            column = @Column(name="mth_used_amt")
    )
    @Column(nullable = false)
    private Money mthUsedAmt;

    public CardMonth(Card card, YearMonth billingMth, Money mthUsedAmt) {
        this.card = requireNonNull(card);
        this.billingMth = requireNonNull(billingMth);
        this.mthUsedAmt = requireNonNull(mthUsedAmt);
    }

    public void addUsedMthAmt(Money money) {
        this.mthUsedAmt = this.mthUsedAmt.add(money);
    }

    public void resetNextMth(YearMonth billingMth) {
        this.mthUsedAmt = this.mthUsedAmt.zero();
        this.billingMth = requireNonNull(billingMth);
    }
}
