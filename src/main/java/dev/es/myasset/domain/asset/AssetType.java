package dev.es.myasset.domain.asset;

import java.util.Arrays;

public enum AssetType {

    CARD, STOCK_ACCOUNT, BANK_ACCOUNT, CASH;

    public static boolean isSyncType(AssetType assetType) {
        return Arrays.stream(AssetType.values())
                .anyMatch(e -> e.name().equals(assetType.name()));
    }

    public static boolean isManualType(AssetType assetType) {
        return assetType.equals(CASH);
    }

}
