package dev.es.myasset.domain.asset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static dev.es.myasset.domain.asset.AssetType.*;
import static dev.es.myasset.domain.asset.CardCode.TEST_CARD;
import static dev.es.myasset.domain.asset.CardMonth.createNewCardMonth;
import static dev.es.myasset.domain.common.UserFixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;

class CardMonthTest {

    private static LocalDate billingDay = LocalDate.of(2020, 1, 1);

    private Card createCard() {

        return Card.builder()
                .asset(Asset.syncAsset(createUser(), CARD))
                .cardCode(TEST_CARD)
                .cardType(CardType.CREDIT)
                .billingDay(billingDay.getDayOfMonth())
                .bankAccount(null)
                .build();
    }

    @Test
    @DisplayName("CardMonth는 기준 날짜의 연월로 초기화된다")
    void createForNewMonthTest() {
        // given
        Card card = createCard();

        // when then
        assertThat(createNewCardMonth(card, billingDay)
                .getBillingMth()
                .equals(billingDay.getMonth()));
    }

}