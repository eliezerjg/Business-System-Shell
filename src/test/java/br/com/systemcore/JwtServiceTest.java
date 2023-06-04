package br.com.systemcore;

import br.com.systemcore.Security.Configurations.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class JwtServiceTest {

    @Mock
    private KeyPair keyPair;

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        MockitoAnnotations.openMocks(this);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
        keyPairGenerator.initialize(ecSpec);
        keyPair = keyPairGenerator.generateKeyPair();

        jwtService.setKeyPair(keyPair);
    }

    @Test
    public void testExtractUsername() {
        String token = createToken("john.doe@example.com");

        String username = jwtService.extractUsername(token);

        Assertions.assertEquals("john.doe@example.com", username);
    }

    @Test
    public void testGenerateToken() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("john.doe@example.com");

        String token = jwtService.generateToken(userDetails);

        Jws<Claims> parsedToken = Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(token);

        Assertions.assertEquals("john.doe@example.com", parsedToken.getBody().getSubject());
    }

    @Test
    public void testIsTokenValid_ValidToken() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("john.doe@example.com");

        String token = createToken("john.doe@example.com");

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        Assertions.assertTrue(isValid);
    }

    @Test
    public void testIsTokenValid_InvalidToken() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("john.doe@example.com");

        String token = createToken("jane.smith@example.com");

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        Assertions.assertFalse(isValid);
    }

    private String createToken(String username) {
        Date expirationDate = Date.from(Instant.now().plusSeconds(1800));
        Date now = Date.from(Instant.now());

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.ES256, keyPair.getPrivate())
                .compact();
    }
}
