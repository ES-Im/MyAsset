package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cash")
public class Cash extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long cashId;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="balance")
    )
    private Money balance;

    @Builder
    public Cash(Asset asset, Money balance) {
        this.asset = requireNonNull(asset);
        this.balance = balance;
    }
}
