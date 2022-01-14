package file.management.com.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;

public class JWT {
    private static final String SECRET_KEY = "67f9efe1461b60c291898b9878f651faa2e6cddf8d4b0e5456475576c2c3c12b5bbd8b0a63574e53e52cb346beac0161e5a9cd1b7452fb57360d7438c830c26b ";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final TemporalAmount TOKEN_VALIDITY = Duration.ofHours(24);

    public static String createJWT(String subject) throws IOException {
        final Instant now = Instant.now();
        final Date expiryDate = Date.from(now.plus(TOKEN_VALIDITY));
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(Date.from(now))
                .setExpiration(expiryDate)
                .setSubject(subject)
                .signWith(SIGNATURE_ALGORITHM, SECRET_KEY);
        return builder.compact();
    }

    public static Jws<Claims> parseJWT(final String compactToken)
            throws ExpiredJwtException,
            UnsupportedJwtException,
            MalformedJwtException,
            SignatureException,
            IllegalArgumentException {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(compactToken);
    }
}
