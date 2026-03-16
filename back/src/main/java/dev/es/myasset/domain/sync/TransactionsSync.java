package dev.es.myasset.domain.sync;

import dev.es.myasset.domain.asset.AssetType;
import dev.es.myasset.domain.shared.NonAuditingEntity;
import dev.es.myasset.domain.transaction.Transactions;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "transactions_sync")
public class TransactionsSync extends NonAuditingEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tran_id", nullable = false)
    private Transactions transactions;

    @Column(nullable = false)
    private String externalId;

    @Enumerated(STRING)
    @Column(nullable = false)
    private AssetType syncAssetType;

    @Enumerated(STRING)
    @Column(nullable = false)
    private RelayProvider relayProvider;

    @Enumerated(STRING)
    @Column(nullable = false)
    private SyncStatus syncStatus;

    @Column(nullable = false)
    private Instant reqAt;

    @Column(nullable = false)
    private Instant completedAt;
}
