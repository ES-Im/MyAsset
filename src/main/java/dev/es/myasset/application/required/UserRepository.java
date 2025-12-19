package dev.es.myasset.application.required;

import dev.es.myasset.domain.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, String> {
    User save(User user);
}
