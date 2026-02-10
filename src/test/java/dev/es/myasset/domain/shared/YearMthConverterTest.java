package dev.es.myasset.domain.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

class YearMthConverterTest {

    @Test
    @DisplayName("YearMonth -> DatabaseColumn(String) 변환")
    void convertToDatabaseColumnTest() {
        // given
        YearMonth yearMonth = YearMonth.of(2020, 1);

        // when then
        assertThat(
                new YearMthConverter().convertToDatabaseColumn(yearMonth)
        ).isEqualTo("2020-01");
    }

    @Test
    @DisplayName("DatabaseColumn(String) -> YeaMonth 변환")
    void convertToEntityAttributeTest() {
        // given
        String yearMonthString = "2020-01";

        // when then
        assertThat(
                new YearMthConverter().convertToEntityAttribute(yearMonthString)
        ).isEqualTo(YearMonth.of(2020, 1));
    }

}