package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.NonAuditingEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.state;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name = "asset_external_mapper",
        uniqueConstraints = @UniqueConstraint(columnNames = {"asset_id", "mapped_key"})
)
public class AssetExtMapper extends NonAuditingEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long mappedId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(STRING)
    @Column(nullable = false)
    private MappedKey mappedKey;
    
    private String idNum;   // mappedKey의 value
    
    private String providerCode;    // 기관 코드 (카드 : CardCode / 은행/증권 : CompanyCode)

    private LocalDateTime linkedAt;     // 연결 시각

    public static AssetExtMapper of(Asset asset, MappedKey mappedKey, String idNum, String providerCode) {
        AssetExtMapper m = new AssetExtMapper();

        m.asset = requireNonNull(asset);
        m.mappedKey = requireNonNull(mappedKey);
        m.idNum = requireNonNull(idNum);
        m.providerCode = validateProviderCode(mappedKey, providerCode);
        m.linkedAt = LocalDateTime.now();

        return m;
    }

    private static String validateProviderCode(MappedKey mappedKey, String providerCode) {
        return switch (mappedKey) {
            case EXTERNAL_USE_ID -> CardCode.valueOf(providerCode).name();
            case FINTECH_USE_NUM -> CompanyCode.valueOf(providerCode).name();
            default -> throw new IllegalArgumentException("지원하지 않는 mappedType: " + mappedKey);
        };
    }
}
