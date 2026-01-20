package dev.es.myasset.domain.sync;

import dev.es.myasset.domain.transaction.Transactions;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "transactions_sync")
public class TransactionsSync {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long tranSyncId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tran_id", nullable = false)
    private Transactions transactions;

    private String external_id;

    @Enumerated(STRING)
    private SyncType syncAssetType;

    @Enumerated(STRING)
    private RelayProvider relayProvider;

    @Enumerated(STRING)
    private SyncStatus syncStatus;

    @Temporal(value = TIMESTAMP)
    private LocalDateTime reqAt;

    @Temporal(value = TIMESTAMP)
    private LocalDateTime completedAt;
}
