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

@Entity
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Getter
@Table(name = "asset")
public class Asset extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long assetId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_key")
    private User user;

    @Enumerated(STRING)
    private AssetType assetType;

}
