package dev.es.myasset.domain.sync;

import dev.es.myasset.domain.asset.Asset;
import dev.es.myasset.domain.asset.AssetType;
import dev.es.myasset.domain.shared.NonAuditingEntity;
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
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"relayProvider", "external_id"}),
        name = "asset_sync"
)
public class AssetSync extends NonAuditingEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long assetSyncId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

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
    private LocalDateTime reqAt;

    @Column(nullable = false)
    private LocalDateTime completedAt;

}
