package dev.es.myasset.adapter.security.edited;

import dev.es.myasset.application.exception.oauth.UnauthenticatedContextException;
import dev.es.myasset.application.required.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityContextManager implements AuthenticatedUser {

    @Override
    public String getAuthenticatedUserKey() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || !auth.isAuthenticated()) throw new UnauthenticatedContextException();

        Object principal = auth.getPrincipal();
        if(principal == null ||
                principal.toString().equals("anonymousUser")) throw new UnauthenticatedContextException();

        return principal.toString();
    }

    @Override
    public String getAuthenticatedUserRole() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || !auth.isAuthenticated()) throw new UnauthenticatedContextException();

        return auth.getAuthorities().stream().findFirst().toString();
    }

}
