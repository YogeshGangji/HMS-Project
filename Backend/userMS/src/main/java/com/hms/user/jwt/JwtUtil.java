package com.hms.user.jwt;

import com.hms.user.exception.HMSException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Long JWT_TOKEN_VALIDITY = 5*60*60L;

    private static final String SECRET = "d410406514a2b2b819b77d6e6cf1d7fde563ae68193cc946503ba3c0cbbebe148cfb6478f60080b8406ac229758614a539223d24d5bc5e7af57e3d26d102812e";

    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes(StandardCharsets.UTF_8)
            );

    public String generateToken(UserDetails userDetails) throws HMSException
    {
        Map<String,Object> claims = new HashMap<>();
        CustomUserDetails user = (CustomUserDetails) userDetails;
    claims.put("id",user.getId());
    claims.put("email",user.getEmail());
    claims.put("name",user.getName());
    claims.put("role",user.getRole());
    claims.put("profileId",user.getProfileId());
    return doGenerateToken(claims, user.getUsername());
    }

    public String doGenerateToken(Map<String, Object> claims, String subject)
    {
       return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }
}
