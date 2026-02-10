package dev.es.myasset.domain.common;

import dev.es.myasset.domain.asset.Asset;
import dev.es.myasset.domain.asset.AssetType;
import dev.es.myasset.domain.category.Category;
import dev.es.myasset.domain.shared.ExpenseType;
import dev.es.myasset.domain.shared.Money;
import dev.es.myasset.domain.transaction.Transactions;
import dev.es.myasset.domain.user.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

import static dev.es.myasset.domain.asset.AssetType.*;
import static dev.es.myasset.domain.category.Category.createCustomCategory;
import static dev.es.myasset.domain.common.AssetFixture.getAsset;
import static dev.es.myasset.domain.common.UserFixture.createUser;
import static dev.es.myasset.domain.transaction.FlowType.OUTFLOW;
import static dev.es.myasset.domain.transaction.PayStatus.APPROVED;
import static dev.es.myasset.domain.transaction.Transactions.createCardTransaction;
import static dev.es.myasset.domain.transaction.Transactions.createNonCardTransaction;

public class TransactionsFixture {

    public static Transactions cardTransaction() {
        User user = createUser();
        Asset asset = getAsset(CARD, user);
        Category category = createCustomCategory(user, "test", ExpenseType.FIXED);

        return createCardTransaction(asset, category, APPROVED, new Money(BigDecimal.ONE), 0);

    }

    public static Stream<Transactions> nonCardTransactions() {
        User user = createUser();

        Category category = createCustomCategory(user, "test", ExpenseType.FIXED);


        return Arrays.stream(AssetType.values())
                .filter(t -> t != CARD)
                .map(t ->
                        createNonCardTransaction(
                                getAsset(t, user)
                                , category
                                , OUTFLOW
                                , new Money(BigDecimal.ONE)
                        )
                );
    }

}
