package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "card")
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long cardId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(value = STRING)
    private CardCode cardCode;  // Mock("000") → 테스트용

    @Enumerated(STRING)
    private CardType cardType;

    private Integer billingDay;

    @Nullable
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "bank_acct_id")
    private BankAccount bankAccount;



}
