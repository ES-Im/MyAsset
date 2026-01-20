package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

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

    private BigDecimal balance;
}
