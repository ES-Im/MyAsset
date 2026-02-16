package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.requireNonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"acct_num", "broker_code"}),
        name="stock_account"
)
public class StockAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long stockAcctId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="asset_id", nullable = false)
    private Asset asset;

    @Column(nullable = false)
    private String acctNum;

    @Enumerated(STRING)
    @Column(nullable = false)
    private CompanyCode brokerCode;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="balance", nullable = false)
    )
    @Column(nullable = false)
    private Money balance;

    @Builder
    public StockAccount(Asset asset, String acctNum, CompanyCode brokerCode, Money balance) {
        this.asset = requireNonNull(asset);
        this.acctNum = requireNonNull(acctNum);
        this.brokerCode = requireNonNull(brokerCode);
        this.balance = requireNonNull(balance);
    }
}
