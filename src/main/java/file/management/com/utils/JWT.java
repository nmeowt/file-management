package file.management.com.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Properties;

public class JWT {
    private static final String FILE_CONFIG = "/config.properties";

    public static String createJWT( String subject, long ttlMillis) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = null;
        JwtBuilder builder = null;
        try {
            String currentDir = System.getProperty("user.dir");
            inputStream = new FileInputStream(currentDir + FILE_CONFIG);
            // load properties from file
            properties.load(inputStream);
            // get property by name
            String secretKey = properties.getProperty("SECRET_KEY");

            //The JWT signature algorithm we will be using to sign the token
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            byte[] apiKeySecretBytes = secretKey.getBytes();
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            //Let's set the JWT Claims
            builder = Jwts.builder()
                    .setIssuedAt(now)
                    .setSubject(subject)
                    .signWith(signatureAlgorithm, signingKey);

            if (ttlMillis > 0) {
                long expMillis = nowMillis + ttlMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        Claims claims = null;
        try {
            String currentDir = System.getProperty("user.dir");
            inputStream = new FileInputStream(currentDir + FILE_CONFIG);
            // load properties from file
            properties.load(inputStream);
            // get property by name
            String secretKey = properties.getProperty("SECRET_KEY");
            byte[] apiKeySecretBytes = secretKey.getBytes();
            claims = Jwts.parser()
                    .setSigningKey(apiKeySecretBytes)
                    .parseClaimsJws(jwt).getBody();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return claims;
    }
}
