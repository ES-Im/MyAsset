package dev.es.myasset.adapter.security.auth;

import dev.es.myasset.application.exception.oauth.UnauthenticatedContextException;
import dev.es.myasset.application.required.CurrentProviderId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityContextManager implements CurrentProviderId {

    @Override
    public String getCurrentProviderId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if(principal == null) {
            throw new UnauthenticatedContextException();
        }
        return principal.toString();
    }
}
