package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.NonAuditingEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.requireNonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name = "asset_external_mapper",
        uniqueConstraints = @UniqueConstraint(columnNames = {"asset_id", "mapped_key", "provider_code"})
)
public class AssetExtMapper extends NonAuditingEntity {
    @Nullable
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "asset_id", nullable = true)
    private Asset asset;

    @Enumerated(STRING)
    @Column(nullable = false)
    private MappedKey mappedKey;
    
    private String idNum;   // mappedKey의 value
    
    private String providerCode;    // 기관 코드 (카드 : CardCode / 은행/증권 : CompanyCode)

    private Instant linkedAt;     // 연결 시각

    public static AssetExtMapper of(Asset asset, MappedKey mappedKey, String idNum, String providerCode) {
        AssetExtMapper m = new AssetExtMapper();

        m.asset = requireNonNull(asset);
        m.mappedKey = requireNonNull(mappedKey);
        m.idNum = requireNonNull(idNum);
        m.providerCode = validateProviderCode(mappedKey, providerCode);
        m.linkedAt = Instant.now();

        return m;
    }

    private static String validateProviderCode(MappedKey mappedKey, String providerCode) {
        return switch (mappedKey) {
            case USER_SEQ_NO -> CardCode.valueOf(providerCode).getCode();
            case FINTECH_USE_NUM -> CompanyCode.valueOf(providerCode).getCode();
            default -> throw new IllegalArgumentException("지원하지 않는 mappedType: " + mappedKey);
        };
    }
}
