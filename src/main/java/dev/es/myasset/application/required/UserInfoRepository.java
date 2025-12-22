package dev.es.myasset.application.required;

import dev.es.myasset.domain.user.UserInfo;
import org.springframework.data.repository.Repository;

/*
 * 회원 개인 정보를 저장하거나 조회.
 */
public interface UserInfoRepository extends Repository<UserInfo, String> {

    UserInfo save(UserInfo userInfo);
}
