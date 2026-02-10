package dev.es.myasset.domain.transaction;


import dev.es.myasset.domain.asset.Asset;
import dev.es.myasset.domain.asset.AssetType;
import dev.es.myasset.domain.category.Category;
import dev.es.myasset.domain.shared.ExpenseType;
import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static dev.es.myasset.domain.asset.AssetType.*;
import static dev.es.myasset.domain.category.Category.createCustomCategory;
import static dev.es.myasset.domain.common.AssetFixture.getAsset;
import static dev.es.myasset.domain.common.UserFixture.createUser;
import static dev.es.myasset.domain.transaction.FlowType.OUTFLOW;
import static dev.es.myasset.domain.transaction.PayStatus.*;
import static dev.es.myasset.domain.transaction.Transactions.createCardTransaction;
import static dev.es.myasset.domain.transaction.Transactions.createNonCardTransaction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionTest {

    @Test
    @DisplayName("카드타입 거래내역 객체 생성 성공 테스트")
    void createCardTransaction_success() {
        // given
        User user = createUser();
        Asset asset = getAsset(CARD, user);
        Category category = createCustomCategory(user, "test", ExpenseType.FIXED);

        // when
        Transactions cardTran = createCardTransaction(
                asset, category, APPROVED, new Money(BigDecimal.ONE), 0
        );

        // then
        assertThat(cardTran)
                .extracting("flowType", "totalInstallCnt")
                .containsExactlyInAnyOrder(OUTFLOW, 0);
    }

    @ParameterizedTest(name = "{index} ==> {0}은 카드타입 거래내역 객체 생성할 수 없는 타입니다.")
    @EnumSource(
            value = AssetType.class,
            names = {"STOCK_ACCOUNT", "BANK_ACCOUNT", "CASH"}
    )
    @DisplayName("카드 타입 거래내역 객체 생성 실패 테스트")
    void createCardTransaction_fail(AssetType assetType) {
        // given
        User user = createUser();
        Asset asset = getAsset(assetType, user);
        Category category = createCustomCategory(user, "test", ExpenseType.FIXED);

        // when then
        assertThatThrownBy(() ->
                createCardTransaction(
                        asset, category, APPROVED, new Money(BigDecimal.ONE), 0)
        ).isInstanceOf(IllegalStateException.class);

    }

    @ParameterizedTest(name = "{index} ==> {0}은 카드타입 이 외 객체 생성 타입이다.")
    @EnumSource(
            value = AssetType.class,
            names = {"STOCK_ACCOUNT", "BANK_ACCOUNT", "CASH"}
    )
    @DisplayName("카드타입 이 외 객체 생성테스트")
    void createNonCardTransactionTest_success(AssetType types) {
        // given
        User user = createUser();
        Asset asset = getAsset(types, user);
        Category category = createCustomCategory(user, "test", ExpenseType.FIXED);

        // when
        Transactions nonCardTran = createNonCardTransaction(asset, category, OUTFLOW, new Money(BigDecimal.ONE));

        // then
        assertThat(nonCardTran)
                .extracting("totalInstallCnt", "memo")
                .containsExactlyInAnyOrder(0, null);
    }

    @Test
    @DisplayName("카드타입 이 외 객체 실패테스트")
    void createNonCardTransactionTest_fail() {
        // given
        User user = createUser();
        Asset asset = getAsset(CARD, user);
        Category category = createCustomCategory(user, "test", ExpenseType.FIXED);

        // when then
        assertThatThrownBy(() ->
                createNonCardTransaction(asset, category, OUTFLOW, new Money(BigDecimal.ONE))
        ).isInstanceOf(IllegalStateException.class);

    }



}
