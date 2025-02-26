package com.api.growin.middlewares;

import java.io.IOException;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.growin.configs.JwtConfig;
import com.api.growin.repositories.RefreshTokenRepository;
import com.api.growin.services.UserDetailsServiceImpl;

import io.micrometer.common.lang.NonNull;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JWT Authentication Filter to process authentication for incoming requests.
 * <p>
 *      This filter intercepts HTTP requests and extracts the JWT token from the 
 *      Authorization header. It validates the token and sets the authenticated user
 *      in the Spring Security context if the token is valid.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Filters incoming requests to check for a valid JWT token.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain to pass control to the next filter.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userId;

        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Validate if the Authorization header is present and starts with "Bearer "
        jwt = authHeader.substring(7);

        // Check if the token is refresh token
        if (jwtConfig.isRefreshToken(jwt)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Extract user ID from the token
        userId = jwtConfig.extractId(jwt).toString();
        
        // Validate token and set authentication context
        if (StringUtils.isNotEmpty(userId) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userId);

            // Check if the access token valid and refresh token exists in database
            if (jwtConfig.isTokenValid(jwt, userDetails) && refreshTokenRepository.findByUserId(UUID.fromString(userId)).isPresent()) {
                // Create an authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}