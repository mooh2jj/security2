package com.example.security2.config.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private static final String securityKey = "Security key aReA";

    @Override
    public String createToken(String subject, long ttlMillis) {

        if (ttlMillis == 0) {
            throw new RuntimeException("토큰 만료기간은 0이상이어야 합니다.");
        }
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        var apiKeySecretBytes = DatatypeConverter.parseBase64Binary(securityKey);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(subject)
                .signWith(signatureAlgorithm, signingKey);

        long nowMillis = System.currentTimeMillis();
        jwtBuilder.setExpiration(new Date(nowMillis + ttlMillis));

        return jwtBuilder.compact();
    }

    @Override
    public String getSubject(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(securityKey))
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    @Override
    public void isUsable(String jwt) throws Exception {
        Jws<Claims> claims = Jwts.parser().setSigningKey(securityKey).parseClaimsJws(jwt);

        log.debug(claims.toString());
    }
}
