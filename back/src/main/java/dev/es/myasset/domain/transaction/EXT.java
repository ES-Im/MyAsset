package dev.es.myasset.domain.transaction;

import java.util.Arrays;

public enum EXT {
    JPG, PNG, JPEG, GIF, WEBP;

    public static Boolean isAvailableExt(String ext) {
        return Arrays.stream(EXT.values())
                .anyMatch(e -> e.name().equals(ext.toUpperCase()));
    }
}
