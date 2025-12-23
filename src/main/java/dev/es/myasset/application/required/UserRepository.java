package dev.es.myasset.application.required;

import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserInfo;
import org.springframework.data.repository.Repository;

/*
 * 회원 계정 정보를 저장하거나 조회
 */
public interface UserRepository extends Repository<User, String> {

    User save(User user);

    User findById(String userKey);

}
