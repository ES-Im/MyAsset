package dev.es.myasset.domain.user;

import dev.es.myasset.adapter.security.auth.CustomUserDetails;
import dev.es.myasset.adapter.security.auth.support.WithMockCustomUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class WithSecurityContextFactoryImpl implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User.registerForTest(
                annotation.userKey(),
                annotation.userStatus(),
                annotation.role(),
                Instant.parse(annotation.createdAt()),
                Instant.parse(annotation.lastLoginAt())
        );

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        context.setAuthentication(auth);

        return context;
    }
}
