package dev.es.myasset.adapter.security.auth.support;

import dev.es.myasset.adapter.security.token.JwtTokenUtil;
import dev.es.myasset.application.UserService;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserRole;
import dev.es.myasset.domain.user.UserStatus;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import static dev.es.myasset.domain.user.UserFixture.createUserWithChange;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class SecurityTestSupport {

    @Autowired protected MockMvc mockMvc;

    @MockitoBean protected UserService userService;

    @Autowired protected UserRepository userRepository;
    @Autowired protected JwtTokenUtil jwtTokenUtil;
    @Autowired protected EntityManager entityManager;


    @Value("${spring.jwt.secret}") String secret;
    SecretKey secretKey;
    @BeforeEach
    void setUp() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    protected void registerUser(String userKey, UserStatus status, UserRole role) {
        User user = createUserWithChange(userKey, status, role);
        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();
    }

    protected String setToken(String tokenType, String userKey, long duration) {
        Instant now = Instant.now();
        Instant expired = now.plusMillis(duration);

        return Jwts.builder()
                .claim("tokenType", tokenType)
                .claim("role", "ROLE_USER")
                .setSubject(userKey)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expired))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    protected String bearer(String userKey, String role) {
        return "Bearer " + jwtTokenUtil.generateAccessToken(userKey, role);
    }
}
