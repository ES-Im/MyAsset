package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.requireNonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"bank_code", "acct_num"}),
        name = "bank_account"
)
public class BankAccount extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyCode bankCode;

    private String productName;

    @Column(nullable = false)
    private String acctNum;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="balance", nullable = false)
    )
    private Money balance;

    private LocalDateTime lastSyncedAt;

    @Builder
    public BankAccount(Asset asset, CompanyCode bankCode, String acctNum, Money balance, LocalDateTime lastSyncedAt) {
        this.asset = requireNonNull(asset);
        this.bankCode = requireNonNull(bankCode);
        this.acctNum = requireNonNull(acctNum);
        this.balance = requireNonNull(balance);
        this.lastSyncedAt = requireNonNull(lastSyncedAt);
    }
}
