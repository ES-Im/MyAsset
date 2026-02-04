package dev.es.myasset.domain.asset;

import dev.es.myasset.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static dev.es.myasset.domain.asset.Asset.*;
import static dev.es.myasset.domain.asset.AssetType.*;
import static dev.es.myasset.domain.common.UserFixture.createUser;
import static org.assertj.core.api.Assertions.*;

public class AssetTest {



    @Test
    @DisplayName("현금성 자산을 유저등록 대상 asset이다.")
    void createAssetTest() {
        // given
        User user = createUser();

        // when then
        createAsset(user, AssetType.CASH);
    }

    private static Stream<Arguments> syncType() {
        return Stream.of(
                CARD, STOCK_ACCOUNT, BANK_ACCOUNT
        ).map(x ->
                Arguments.of("{type} ==> " + x, x)
        );
    }

    @ParameterizedTest
    @MethodSource("syncType")
    @DisplayName("현금성 자산 외 자산은 유저가 등록을 할 수 없다.")
    void cantCreateAsset(String description, AssetType given) {
        // given
        User user = createUser();

        // when then
         assertThatThrownBy(() ->
                 createAsset(user, given)
         ).isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @MethodSource("syncType")
    @DisplayName("현금 외 자산은 동기화 대상 asset이다.")
    void syncAssetTest(String description, AssetType given) {
        // given
        User user = createUser();

        // when then
        syncAsset(user, given);
    }

}
