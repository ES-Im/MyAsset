package dev.es.myasset.domain.common;

import dev.es.myasset.domain.asset.Asset;
import dev.es.myasset.domain.asset.AssetType;
import dev.es.myasset.domain.user.User;

public class AssetFixture {

    public static Asset getAsset(AssetType types, User user) {
        if(types.isSyncType()) {
            return Asset.syncAsset(user, types);
        }

        return Asset.createAsset(user, types);
    }
}
