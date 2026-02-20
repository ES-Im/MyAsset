package dev.es.myasset.adapter.security.auth;

import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserKey(username)
                .orElseThrow(()
                        -> new UsernameNotFoundException("사용자를 찾을 수 없습니다")
        );

        return new CustomUserDetails(user);
    }
}
