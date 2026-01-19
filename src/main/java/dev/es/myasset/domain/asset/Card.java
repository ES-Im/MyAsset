package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "card")
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    private CardCode cardCode;  // Mock("000") → 테스트용

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private Integer billingDay;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_acct_id")
    private BankAccount bankAccount;

}
