package dev.es.myasset.domain.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    // BUDGET
    BUDGET_EXCEEDED("예산 초과")

    // SPEND
    , DAILY_SPEND_SUMMARY("일일 지출 요약")
    , WEEKLY_SPEND_SUMMARY("주간 지출 요약")
    , MONTHLY_SPEND_SUMMARY("월간 지출 요약")

    // CARD
    , CARD_PAYMENT_DUE("카드 결제일 도래 (1일 전)")

    // STOCK
    , STOCK_PRICE_SURGE("보유 주식 급등")
    , STOCK_PRICE_DROP("보우 주식 급락")

    // SPENT
    , ABNORMAL_REPEATED_TRANSACTION("비정상적인 동일 지출")
    , ABNORMAL_EXPENSE("예산의 30% 이상 지출건");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }
}
