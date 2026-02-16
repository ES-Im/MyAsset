package dev.es.myasset.domain.notification;

import dev.es.myasset.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static dev.es.myasset.domain.common.UserFixture.createUser;
import static dev.es.myasset.domain.notification.Notification.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class NotificationTest {

    @Test
    @DisplayName("알림 커스텀 객체 생성 성공시, 읽음여부는 false이다.")
    void createNotification_success() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2020,1,1,0,0, 0);
        User user = createUser();

        // when then
        assertThat(
                createNotification(user, dateTime).getIsRead()
        ).isEqualTo(false);

    }

    @TestFactory
    @DisplayName("알림 커스텀 객체 생성 후 알림 숨기기까지 테스트")
    Stream<DynamicTest> hideTests() {
        LocalDateTime dateTime = LocalDateTime.of(2020,1,1,0,0, 0);
        User user = createUser();

        Notification noti = createNotification(user, dateTime);

        return Stream.of(
                DynamicTest.dynamicTest("notification 엔티티 생성시, 알림속성값은 false(숨기지않음)이다", () ->
                    //then
                    assertThat(noti.getIsHide()).isEqualTo(false)
                ),
                DynamicTest.dynamicTest("알림을 false(숨기지않음) 상태에서 true(숨김)으로 바꿀 수 있다.", () -> {
                    // when
                    noti.markHide();
                    // then
                    assertThat(noti.getIsHide()).isEqualTo(true);
                }),
                DynamicTest.dynamicTest("알림이 true(숨김) 상태에서는 숨김 상태로 변경할 수 없다.", () -> {
                    // then
                     assertThatThrownBy(() ->
                             noti.markHide()
                     ).isInstanceOf(IllegalStateException.class);
                })

        );
    }

}