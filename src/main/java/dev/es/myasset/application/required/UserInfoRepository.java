package dev.es.myasset.application.required;

import dev.es.myasset.domain.user.UserInfo;
import org.springframework.data.repository.Repository;

/*
 * 회원 개인정보 정책과 관련하여 Null 처리를 위한 인터페이스 (batch/정책 처리에서 이용)
 */
public interface UserInfoRepository extends Repository<UserInfo, String> {

    UserInfo save(UserInfo userInfo);

    UserInfo findByProviderId(String providedId);

//    @Query("""
//        update user_info (username, email, providerType)
//           set (null, null, null)
//         where user_key == :userKey
//    """)
//    int nullifyUserInfo(@Param("userKey") String userKey);

}
