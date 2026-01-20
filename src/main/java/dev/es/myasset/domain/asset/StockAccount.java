package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="stock_account")
public class StockAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long stockAcctId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="asset_id", nullable = false)
    private Asset asset;

    private CompanyCode brokerCode;

}
