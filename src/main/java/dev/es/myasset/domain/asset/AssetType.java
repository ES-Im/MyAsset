package dev.es.myasset.domain.asset;

import lombok.Getter;

@Getter
public enum AssetType {

    CARD(true)
    , STOCK_ACCOUNT(true)
    , BANK_ACCOUNT(true)
    , CASH(false);

    private final boolean isSyncType;

    AssetType(boolean isSyncType) {
        this.isSyncType = isSyncType;
    }

    public static boolean isSyncType(AssetType assetType) {
        return assetType.isSyncType;
    }

    public static boolean isManualType(AssetType assetType) {
        return !assetType.isSyncType;
    }

}
