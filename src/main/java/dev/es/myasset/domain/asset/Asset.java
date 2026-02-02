package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Entity
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Getter
@Table(name = "asset")
public class Asset extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long assetId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_key", nullable = false)
    private User user;

    @Enumerated(STRING)
    private AssetType assetType;

    private static Asset create(User user, AssetType assetType) {
        Asset asset = new Asset();

        asset.user = requireNonNull(user);
        asset.assetType = requireNonNull(assetType);

        return asset;
    }

    public static Asset syncAsset(User user, AssetType assetType) {
        state(AssetType.isSyncType(assetType), "동기화 대상 자산종류가 아닙니다 : " + assetType);

        return create(user, assetType);
    }

    public static Asset createAsset(User user, AssetType assetType) {
        state(AssetType.isManualType(assetType), "수동등록 대상 자산종류가 아닙니다 : " + assetType);

        return create(user, assetType);
    }

}
