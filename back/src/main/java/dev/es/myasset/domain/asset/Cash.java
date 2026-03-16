package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.Money;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.requireNonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cash")
public class Cash extends BaseEntity {

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="balance")
    )
    @Column(nullable = false)
    private Money balance;

    @Builder
    public Cash(Asset asset, Money balance) {
        this.asset = requireNonNull(asset);
        this.balance = requireNonNull(balance);
    }
}
