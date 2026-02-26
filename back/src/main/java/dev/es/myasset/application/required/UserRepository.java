package dev.es.myasset.application.required;

import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/*
 * 회원 계정 정보를 저장하거나 조회
 */
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserKey(String UserKey);

    @Query("select u.status from User u where u.userKey = :userKey")
    Optional<UserStatus> findUserStatusByUserKey(@Param("userKey")String userKey);

}
