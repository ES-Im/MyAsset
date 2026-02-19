package dev.es.myasset.application.required;

import dev.es.myasset.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * 회원 계정 정보를 저장하거나 조회
 */
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserKey(String UserKey);

}
