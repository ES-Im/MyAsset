package dev.es.myasset.domain.user;

import dev.es.myasset.domain.shared.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    private static UserInfo createWithEmail(String email) {
        String uuid = UUID.randomUUID().toString();
        String username = "홍길동";

        return UserInfo.registerUserInfo(
            "kakao", uuid, email, username
        );
    }

    private static UserInfo createWithProviderType(String providerType) {
        String uuid = UUID.randomUUID().toString();
        String username = "홍길동";

        return UserInfo.registerUserInfo(
            providerType, uuid, "abc@naver.com", username
        );
    }

    @Test
    @DisplayName("이메일 형식이 맞아야 등록이 된다.")
    void validateEmail() {
        // given
        String email = "abc@google.co.kr";
        UserInfo testInfo = createWithEmail(email);

        // then
        assertThat(testInfo.getEmail()).isInstanceOf(Email.class);
    }

    @Test
    @DisplayName("이메일 형식이 맞지 않으면 등록이 되지 않는다.")
    void cantValidateEmail() {
        // given
        String email = "abc@google.";

        // then
         assertThatThrownBy(() -> createWithEmail(email)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> checkProviderType() {

        return Stream.of(
                "kakao", "google", "naver", "KAKAO", "GOOGLE", "NAVER"
        ).map(t ->
                Arguments.of("{type} ==> " + t, t)
        );
    }

    @ParameterizedTest
    @MethodSource("checkProviderType")
    @DisplayName("providerType는 대소문자 구분없이 google, naver, kakao이다")
    void checkProviderType(String description, String given) {
        // given, then
        assertDoesNotThrow(() -> createWithProviderType(given));
    }

    @Test
    @DisplayName("providerType 예외 테스트")
    void cantCheckProviderType() {
        // given
        String wrongType = "wrongType";
        // when // then
         assertThatThrownBy(() -> createWithProviderType(wrongType))
                 .isInstanceOf(IllegalArgumentException.class);
    }


}