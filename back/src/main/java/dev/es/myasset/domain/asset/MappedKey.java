package dev.es.myasset.domain.asset;

import lombok.Getter;

@Getter
public enum MappedKey {
    USER_SEQ_NO,   // 카드 정보 요청시 필요한 식별 정보
    FINTECH_USE_NUM    // 계좌 정보 요청시 필요한 식별 정보
}
