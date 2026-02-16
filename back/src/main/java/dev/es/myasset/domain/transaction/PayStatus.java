package dev.es.myasset.domain.transaction;

import lombok.Getter;

@Getter
public enum PayStatus {
    APPROVED("01", "승인"),
    CANCELLED("02", "승인취소"),
    CORRECTED("03", "정정"),
    NO_AUTH("04", "무승인매입");

    private final String code;
    private final String label;

    PayStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
