package dev.es.myasset.domain.sync;

import dev.es.myasset.domain.shared.NonAuditingEntity;
import dev.es.myasset.domain.transaction.Transactions;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "transactions_sync")
public class TransactionsSync extends NonAuditingEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long tranSyncId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tran_id", nullable = false)
    private Transactions transactions;

    @Column(nullable = false)
    private String external_id;

    @Enumerated(STRING)
    @Column(nullable = false)
    private SyncType syncAssetType;

    @Enumerated(STRING)
    @Column(nullable = false)
    private RelayProvider relayProvider;

    @Enumerated(STRING)
    @Column(nullable = false)
    private SyncStatus syncStatus;

    @Column(nullable = false)
    private LocalDateTime reqAt;

    @Column(nullable = false)
    private LocalDateTime completedAt;
}
