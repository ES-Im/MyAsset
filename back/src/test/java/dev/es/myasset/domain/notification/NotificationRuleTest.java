package dev.es.myasset.domain.notification;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dev.es.myasset.domain.notification.NotificationRule.*;
import static org.assertj.core.api.Assertions.assertThat;

class NotificationRuleTest {

    @Test
    @DisplayName("디폴트알림 생성 시, 해당 규칙의 사용자 정보는 없다")
    void createDefaultRuleTest() {
        assertThat(
                createDefaultRule(NotificationType.ABNORMAL_EXPENSE).getUser()
        ).isNull();
    }

// to-do : repository 레벨에서 테스트(uniqueConstraint)
//    @Test
//    @DisplayName("사용자는 이미 차단한 알림을 중복해서 차단할 수 없다.")
//    void createUserBlockRule_success() {
//        // given
//        User user = createUser();
//        NotificationType notiType = NotificationType.ABNORMAL_EXPENSE;
//
//        // when
//        NotificationRule rule = createUserBlockRule(user, notiType);
//
//        // then
//    }

}