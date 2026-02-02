package dev.es.myasset.domain.transaction;

import dev.es.myasset.domain.asset.Asset;
import dev.es.myasset.domain.category.Category;
import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.Money;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static dev.es.myasset.domain.asset.AssetType.CARD;
import static dev.es.myasset.domain.transaction.FlowType.OUTFLOW;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "transactions")
public class Transactions extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long tran_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="cgy_id", nullable = true)
    private Category category;

    @Enumerated(STRING)
    private FlowType flowType;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="tran_amt")
    )
    private Money tranAmt; // 거래 금액

    @Nullable
    @Enumerated(STRING)
    private PayStatus payStatus;    // 거래 상태

    @Nullable
    private Integer totalInstallCnt;    // null = 카드 아님 or 일시불

    @Nullable
    private String memo;

    @Builder
    protected Transactions(Asset asset, Category category, FlowType flowType, PayStatus payStatus, Money tranAmt, Integer totalInstallCnt, String memo) {
        this.asset = requireNonNull(asset);
        this.category = category;
        this.flowType = requireNonNull(flowType);
        this.payStatus = payStatus;
        this.tranAmt = requireNonNull(tranAmt);
        this.totalInstallCnt = totalInstallCnt;
        this.memo = memo;
    }

    public static Transactions createCardTransaction (
            Asset asset, Category category
            , PayStatus payStatus, Money tranAmt, Integer totalInstallCnt
    ) {
        state(asset.getAssetType().equals(CARD), "타입에 맞지 않는 타입 : CARD 거래내역이 아닙니다");

        return Transactions.builder()
                    .asset(asset).category(category)
                    .payStatus(payStatus).flowType(OUTFLOW)
                    .tranAmt(tranAmt).totalInstallCnt(totalInstallCnt)
                    .memo(null)
                    .build();
    }

    public static Transactions createNonCardTransaction (
            Asset asset, Category category
            , FlowType flowType, Money tranAmt
    ) {
        state(!asset.getAssetType().equals(CARD), "타입에 맞지 않는 타입 : CARD 거래내역입니다");

        return Transactions.builder()
                    .asset(asset).category(category)
                    .flowType(flowType).tranAmt(tranAmt)
                    .payStatus(null).totalInstallCnt(null)
                    .memo(null)
                    .build();
    }

    public void addMemo(String memo) {
        this.memo = memo;
    }

}
