package dev.es.myasset.domain.transaction;

import dev.es.myasset.domain.asset.Asset;
import dev.es.myasset.domain.category.Category;
import dev.es.myasset.domain.shared.BaseEntity;
import dev.es.myasset.domain.shared.Money;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private Long tranId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="asset_id", nullable = false)
    private Asset asset;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="cgy_id", nullable = true)
    private Category category;

    @Enumerated(STRING)
    @Column(nullable = false)
    private FlowType flowType;

    @Embedded
    @AttributeOverride(
            name="money",
            column = @Column(name="tran_amt", nullable = false)
    )
    private Money tranAmt; // 거래 금액

    @Nullable
    @Enumerated(STRING)
    private PayStatus payStatus;    // 거래 상태

    @Nullable
    private Integer totalInstallCnt;    // 0 = 카드 아님 or 일시불

    @Nullable
    private String memo;

    public static Transactions createCardTransaction (
            Asset asset, Category category
            , PayStatus payStatus, Money tranAmt, Integer totalInstallCnt
    ) {
        state(asset.getAssetType().equals(CARD), "타입에 맞지 않는 타입 : CARD 거래내역이 아닙니다");

        Transactions transactions = new Transactions();

        transactions.asset = requireNonNull(asset);
        transactions.category = category;
        transactions.flowType = OUTFLOW;
        transactions.payStatus = requireNonNull(payStatus);
        transactions.tranAmt = requireNonNull(tranAmt);
        transactions.totalInstallCnt = (totalInstallCnt == null)? 0 : totalInstallCnt;

        return transactions;
    }

    public static Transactions createNonCardTransaction (
            Asset asset, Category category
            , FlowType flowType, Money tranAmt
    ) {
        state(!asset.getAssetType().equals(CARD), "타입에 맞지 않는 타입 : CARD 거래내역입니다");

        Transactions transactions = new Transactions();

        transactions.asset = requireNonNull(asset);
        transactions.category = category;
        transactions.flowType = flowType;
        transactions.tranAmt = requireNonNull(tranAmt);
        transactions.totalInstallCnt = 0;

        return transactions;
    }

    public void addMemo(String memo) {
        this.memo = memo;
    }

}
