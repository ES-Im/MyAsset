package dev.es.myasset.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static dev.es.myasset.domain.user.UserStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    private User createUser() {
        return User.register(Instant.parse("2026-01-01T00:00:00+09:00"));
    }

    @Test
    @DisplayName("status 변환 - 소셜 회원가입 즉시 ACTIVE 상태이다.")
    void activateUser() {
        // given
        User hongUser = createUser();

        // then
        assertThat(hongUser.getStatus()).isEqualTo(ACTIVE);
        assertThat(hongUser.getRole()).isEqualTo(UserRole.ROLE_USER);
    }

    @Test
    @DisplayName("status 변환 - 1년간 미접속 시 DORMANT 상태가 된다.")
    void markDormant() {
        // given
        User hongUser = createUser();

        //when
        hongUser.markDormant(hongUser.getLastLoginAt().plus(366, ChronoUnit.DAYS));

        //then
        assertThat(hongUser.getStatus()).isEqualTo(DORMANT);
    }

    @Test
    @DisplayName("status 변환 - 1년 내 미접속 시 DORMANT 상태가 되지 않는다")
    void notMarkDormant() {
        // given
        User hongUser = createUser();

        // when then
        assertThatThrownBy(() ->
             hongUser.markDormant(hongUser.getLastLoginAt().plus(364, ChronoUnit.DAYS))
        ).isInstanceOf(IllegalStateException.class);
    }


    @TestFactory
    @DisplayName("사용자 탈퇴요청 -> 탈퇴 시나리오")
    Stream<DynamicTest> withdrawUserScenario() {
        // given
        User hongUser = createUser();

        return Stream.of(
                DynamicTest.dynamicTest("사용자 탈퇴 요청 시, WITHDRAW_PENDING가 된다.", () -> {
                    // when
                    hongUser.requestWithdraw(Instant.parse("2026-01-01T00:00:00+09:00"));

                    // then
                    assertThat(hongUser.getStatus()).isEqualTo(WITHDRAW_PENDING);
                }),
                DynamicTest.dynamicTest("사용자 탈퇴 요청 후 30일이 초과되지 않으면, WITHDRAW 상태가 되지 않는다", () -> {
                    // when
                     assertThatThrownBy(() -> hongUser.markWithdraw(hongUser.getWithdrawReqAt().plus(30, ChronoUnit.DAYS)))
                             .isInstanceOf(IllegalStateException.class);
                }),
                DynamicTest.dynamicTest("사용자 탈퇴 요청 후 30일이 초과되면, WITHDRAW 상태가 된다.", () -> {
                    // when
                    hongUser.markWithdraw(hongUser.getWithdrawReqAt().plus(31, ChronoUnit.DAYS));

                    // then
                    assertThat(hongUser.getStatus()).isEqualTo(WITHDRAWN);
                }),
                DynamicTest.dynamicTest("WITHDRAW 상태 후 90일이 지나지 않으면 회원정보가 삭제되지 않는다.", () -> {
                     assertThatThrownBy(() -> hongUser.checkDeleteProperty(
                             hongUser.getWithdrawReqAt().plus(90, ChronoUnit.DAYS))
                     ).isInstanceOf(IllegalStateException.class);
                }),
                DynamicTest.dynamicTest("WITHDRAW 상태 후 90일이 지나면 회원정보가 삭제된다.", () -> {
                    assertThat(hongUser.checkDeleteProperty(
                            hongUser.getWithdrawReqAt().plus(91, ChronoUnit.DAYS)
                    )).isTrue();
                })
        );
    }

    private static Stream<Arguments> beforeRequestActivatedStatus() {
        Instant now = Instant.now();
        return Stream.of(
                Arguments.of("탈퇴요청상태에서 계정을 다시 활성화 할 수 있다.",
                        (Consumer<User>) u -> u.requestWithdraw(now)),
                Arguments.of("휴먼상태에서 계정을 다시 활성화 할 수 있다.",
                        (Consumer<User>) u -> u.markDormant(u.getLastLoginAt().plus(366, ChronoUnit.DAYS)))
        );
    }

    @DisplayName("사용자 계정활성화 시나리오")
    @ParameterizedTest(name = "{0}")
    @MethodSource("beforeRequestActivatedStatus")
    void requestActive(String description, Consumer<User> given) {
        // given
        User hongUser = createUser();
        Instant now = Instant.now();

        given.accept(hongUser);

        // when
        hongUser.requestActive(now);

        // then
        assertThat(hongUser.getStatus()).isEqualTo(ACTIVE);
    }

    @Test
    @DisplayName("사용자 계정활성화 예외 시나리오 : 탈퇴상태에서는 계정을 활성화 할 수 없다.")
    void cantRequestActivate() {
        // given
        User hongUser = createUser();
        Instant now = Instant.now();
        hongUser.requestWithdraw(now.minus(31, ChronoUnit.DAYS));
        hongUser.markWithdraw(now);

        // when
        // then
         assertThatThrownBy(() -> hongUser.requestActive(now))
                 .isInstanceOf(IllegalStateException.class);
    }



}