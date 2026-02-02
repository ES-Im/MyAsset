package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.requireNonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"bank_code", "acct_num"}),
        name = "bank_account"
)
public class BankAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long bankAcctId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyCode bankCode;

    @Column(nullable = false)
    private String acctNum;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="balance")
    )
    @Column(nullable = false)
    private Money balance;

    @Builder
    public BankAccount(Asset asset, CompanyCode bankCode, String acctNum, Money balance) {
        this.asset = requireNonNull(asset);
        this.bankCode = requireNonNull(bankCode);
        this.acctNum = requireNonNull(acctNum);
        this.balance = balance;
    }
}
