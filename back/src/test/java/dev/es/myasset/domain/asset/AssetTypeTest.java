package dev.es.myasset.domain.asset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static dev.es.myasset.domain.asset.AssetType.*;
import static org.assertj.core.api.Assertions.assertThat;

class AssetTypeTest {

    private static Stream<Arguments> syncTypes() {
        return Stream.of(
                Arguments.of(CARD, true),
                Arguments.of(STOCK_ACCOUNT, true),
                Arguments.of(BANK_ACCOUNT, true),
                Arguments.of(CASH, false)
        );
    }

    @MethodSource("syncTypes")
    @DisplayName("자산 타입이 동기화 대상인지 체크한다.")
    @ParameterizedTest(name = "{index} ==>: ''{0}'' 일때 동기화 가능여부는 ''{1}''이다.")
    void isSyncTypeTest(AssetType assetType, boolean expect) {

        assertThat(isSyncType(assetType)).isEqualTo(expect);
    }

    private static Stream<Arguments> manualTypes() {
        return Stream.of(
                Arguments.of(CARD, false),
                Arguments.of(STOCK_ACCOUNT, false),
                Arguments.of(BANK_ACCOUNT, false),
                Arguments.of(CASH, true)
        );
    }

    @MethodSource("manualTypes")
    @DisplayName("자산 타입이 수기 등록 대상인지 체크한다.")
    @ParameterizedTest(name = "{index} ==> ''{0}''일 때 수기 등록 가능여부는 ''{1}''이다.")
    void isManualTypeTest(AssetType assetType, boolean expect) {
        assertThat(isManualType(assetType)).isEqualTo(expect);
    }
}