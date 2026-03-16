package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.NonAuditingEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.time.YearMonth;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.requireNonNull;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "card")
public class Card extends NonAuditingEntity {

    private String cardNumMasked;   // 마스킹된 카드번호

    private String cardName;    // 카드 상품명

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private CardCode cardCode;  // Mock("000") → 테스트용

    @Enumerated(STRING)
    @Column(nullable = false)
    private CardType cardType;

    @Column(nullable = false)
    private Integer billingDay;

    @Nullable
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "bank_acct_id")
    private BankAccount bankAccount;

    private YearMonth lastSyncedChargeYearMonth;

    private Long lastSyncedSettlementSeqNo; // 주) 카드청구기본정보조회 응답 메시지 상의 청구년월 및 결제순번

    @Builder
    public Card(Asset asset,
                CardCode cardCode,
                CardType cardType,
                Integer billingDay,
                BankAccount bankAccount,
                Long lastSyncedSettlementSeqNo) {
        this.asset = requireNonNull(asset);
        this.cardCode = requireNonNull(cardCode);
        this.cardType = requireNonNull(cardType);
        this.billingDay = requireNonNull(billingDay);
        this.bankAccount = bankAccount;
        this.lastSyncedSettlementSeqNo = lastSyncedSettlementSeqNo;
    }
}
