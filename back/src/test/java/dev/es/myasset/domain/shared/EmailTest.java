package dev.es.myasset.domain.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @ValueSource(strings = {"abc@gmail.com", "0@naver.com", "sadjldklasddjald28@kakao.com"})
    @DisplayName("이메일 형식이 맞아야 등록이 된다.")
    void validateEmail_success(String value) {
        assertThat(new Email(value));
    }

    @ParameterizedTest(name = "{index} ==> ''{0}''")
    @ValueSource(strings = {"abc", "acb@naver.", "abc@", "@naver.com"})
    @DisplayName("이메일 형식이 맞지 않으면 등록이 되지 않는다")
    void validateEmail_fail(String value) {
        assertThatThrownBy(() -> new Email(value)).isInstanceOf(IllegalArgumentException.class);
    }

}