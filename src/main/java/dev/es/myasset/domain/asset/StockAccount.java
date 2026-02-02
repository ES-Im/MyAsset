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
@Table(name="stock_account")
public class StockAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long stockAcctId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="asset_id", nullable = false)
    private Asset asset;

    @Enumerated(STRING)
    private CompanyCode brokerCode;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="balance")
    )
    private Money balance;

    @Builder
    public StockAccount(Asset asset, CompanyCode brokerCode, Money balance) {
        this.asset = requireNonNull(asset);
        this.brokerCode = requireNonNull(brokerCode);
        this.balance = balance;
    }
}
