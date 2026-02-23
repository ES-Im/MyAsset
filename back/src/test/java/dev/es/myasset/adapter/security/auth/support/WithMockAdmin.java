package dev.es.myasset.adapter.security.auth.support;

import dev.es.myasset.domain.user.UserRole;
import dev.es.myasset.domain.user.UserStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@WithMockCustomUser(userStatus = UserStatus.ACTIVE, role= UserRole.ROLE_ADMIN)
public @interface WithMockAdmin {
}
