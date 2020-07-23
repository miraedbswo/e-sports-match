package com.dsm.umjunsik.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dsm.umjunsik.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Value("${auth.jwt.exp.access}")
    private int accessTokenExpiration;

    @Value("${auth.jwt.header}")
    private String header;

    @Value("${auth.jwt.prefix}")
    private String prefix;

    private final CustomUserDetailsService customUserDetailsService;

    public String createAccessToken(String id) {
        return JWT.create()
                .withSubject(id)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000))
                .withIssuedAt(new Date())
                .withIssuer("dbswo")
                .withClaim("id", id)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public Authentication getAuthentication(String token) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(getSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token)
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(header);
        return (bearerToken != null && bearerToken.startsWith(prefix))
                ? bearerToken.substring(7) : null;
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            return JWT.require(Algorithm.HMAC512(secretKey))
                    .withIssuer("dbswo")
                    .build()
                    .verify(accessToken)
                    .getExpiresAt()
                    .after(new Date());
        } catch (JWTVerificationException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

}