package com.api.growin.configs;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.api.growin.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;

/**
 * JWT Configuration Service.
 * <p>
 *      This service provides functionality for generating, extracting, and validating JSON Web Tokens (JWT).
 *      It is responsible for handling authentication and token security in the application.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Service
public class JwtConfig {

    @Autowired
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

     /**
     * Constructor for JwtConfig.
     * 
     * @param userDetailsService The service used to load user details.
     */
    public JwtConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Extracts the user ID from the JWT token.
     *
     * @param token The JWT token.
     * @return The extracted user ID.
     */
    public String extractId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isRefreshToken(String token) {
        return extractClaim(token, claims -> claims.get("refresh") != null);
    }

    public LocalDateTime extractExpireAt(String token) {
        return extractExpiration(token).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Extracts a specific claim from the JWT token.
     * 
     * @param <T>            The type of the claim to be extracted.
     * @param token          The JWT token.
     * @param claimsResolver A function to resolve the desired claim.
     * @return The extracted claim value.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the given user details.
     * 
     * @param userDetails The user details.
     * @return A JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional claims.
     * 
     * @param extraClaims Additional claims to be included in the token.
     * @param userDetails The user details.
     * @return A JWT token.
     */
    @SuppressWarnings("deprecation")
    public String generateToken(Map<String, Objects> extraClaims, UserDetails userDetails) {
        User user = (User) userDetails;
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 1 day
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates whether a JWT token is valid for a given user.
     * 
     * @param token       The JWT token.
     * @param userDetails The user details.
     * @return {@code true} if the token is valid, otherwise {@code false}.
     */
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String id = extractId(token);
        return (id.equals(userDetails.getUsername().toString()) && !isTokenExpired(token));
    }

    /**
     * Checks if the JWT token has expired.
     * 
     * @param token The JWT token.
     * @return {@code true} if the token has expired, otherwise {@code false}.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     * 
     * @param token The JWT token.
     * @return The expiration date.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     * 
     * @param token The JWT token.
     * @return The claims contained in the token.
     */
    @SuppressWarnings("deprecation")
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Retrieves the signing key used for JWT token encryption.
     * 
     * @return The signing key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a refresh token for the given user details.
     * 
     * @param extraClaims Additional claims to be included in the refresh token.
     * @param userDetails The user details.
     * @return A refresh token.
     */
    @SuppressWarnings("deprecation")
    public String generateRefresh(Map<String, Object> extraClaims, UserDetails userDetails) {
        User user = (User) userDetails;

        /* Adding refresh claims */
        extraClaims.put("refresh", true);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Retrieves the user ID from the JWT token.
     * 
     * @param token The JWT token.
     * @return The extracted user ID.
     */
    public String getIdFromToken(String token) {
        return extractId(token);
    }

    /**
     * Validates the JWT token.
     * <p>
     *      This method extracts the user ID from the token, checks if it is not empty,
     *      verifies that the token is not expired, and ensures that it matches the stored
     *      user details.
     * </p>
     * 
     * @param token The JWT token.
     * @return {@code true} if the token is valid, otherwise {@code false}.
     */
    public Boolean validateToken(String token) {
        String userEmail = extractId(token);
        if (StringUtils.isNotEmpty(userEmail) && !isTokenExpired(token)) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            User user = (User) userDetails;
            return isTokenValid(token, user);
        }

        return false;
    }
}
