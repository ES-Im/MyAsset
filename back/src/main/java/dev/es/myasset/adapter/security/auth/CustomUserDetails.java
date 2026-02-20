package dev.es.myasset.adapter.security.auth;

import dev.es.myasset.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails (
        User user
) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    @Override
    public String getUsername() {
        return user.getUserKey();
    }

    @Override
    public String getPassword() {
        return null;
    }
}
