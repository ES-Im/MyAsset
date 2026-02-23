package dev.es.myasset.adapter.security.auth.support;

import dev.es.myasset.domain.user.UserRole;
import dev.es.myasset.domain.user.UserStatus;
import dev.es.myasset.domain.user.WithSecurityContextFactoryImpl;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.METHOD})
@WithSecurityContext(factory = WithSecurityContextFactoryImpl.class)
public @interface WithMockCustomUser {

    String userKey() default "testRandomKey";
    UserStatus userStatus() default UserStatus.ACTIVE;
    UserRole role() default UserRole.ROLE_USER;

    String createdAt() default "2026-01-01T00:00:00";
    String lastLoginAt() default "2026-01-01T00:00:00";
}
