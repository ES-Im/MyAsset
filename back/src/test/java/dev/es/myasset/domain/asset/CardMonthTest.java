package dev.es.myasset.domain.asset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;

import static dev.es.myasset.domain.asset.AssetType.CARD;
import static dev.es.myasset.domain.asset.CardCode.TEST_CARD;
import static dev.es.myasset.domain.asset.CardMonth.createNewCardMonth;
import static dev.es.myasset.domain.user.UserFixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;

class CardMonthTest {

    private final static Instant billingDay = Instant.parse("2020-01-01T00:00:00.00+09:00");

    private Card createCard() {

        return Card.builder()
                .asset(Asset.syncAsset(createUser(), CARD))
                .cardCode(TEST_CARD)
                .cardType(CardType.CREDIT)
                .billingDay(billingDay.atZone(ZoneId.of("Asia/Seoul")).getMonthValue())
                .bankAccount(null)
                .build();
    }

    @Test
    @DisplayName("CardMonth는 기준 날짜의 연월로 초기화된다")
    void createForNewMonthTest() {
        int billingDayMonth = billingDay.atZone(ZoneId.of("Asia/Seoul")).getMonthValue();

        // given
        Card card = createCard();

        // when then
        CardMonth newCardMonth = createNewCardMonth(card, Instant.from(billingDay.atZone(ZoneId.of("Asia/Seoul"))));
        int monthValue = newCardMonth.getBillingMth().getMonthValue();

        assertThat(monthValue == billingDayMonth).isTrue();
    }

}