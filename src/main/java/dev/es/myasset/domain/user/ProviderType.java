package dev.es.myasset.domain.user;

public enum ProviderType {
    KAKAO, GOOGLE, NAVER;

    public static ProviderType from(String value) {

        try {
            return ProviderType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
