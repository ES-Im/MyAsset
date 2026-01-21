package dev.es.myasset.domain.transaction;

import dev.es.myasset.domain.asset.Asset;
import dev.es.myasset.domain.category.Category;
import dev.es.myasset.domain.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "transactions")
public class Transactions extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long tran_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="asset_id", nullable = true)
    private Asset asset;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="cgy_id", nullable = true)
    private Category category;

    @Enumerated(STRING)
    private FlowType flowType;

    @Enumerated(STRING)
    private PayStatus payStatus;

    private BigDecimal tranAmt;

    private Integer totalInstallCnt;

    private String memo;

}
