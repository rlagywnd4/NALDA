package com.sparta.nalda.filter;

import com.sparta.nalda.util.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


@RequiredArgsConstructor
public class JWFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            String authorization = request.getHeader("Authorization");

            if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorization.substring(7);

            if (tokenProvider.isExpired(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
                return;
            }

            String userId = tokenProvider.getId(token);
            String email = tokenProvider.getEmail(token);
            String userRole = tokenProvider.getUserRole(token);


            Map<String, Object> principal = new HashMap<>();
            principal.put("id", userId);
            principal.put("email", email);

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                    principal, null, List.of(() -> userRole)
                );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }  catch (SecurityException | MalformedJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
            return;
        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
            return;
        } catch (UnsupportedJwtException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
            return;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않는 JWT 토큰입니다.");
            return;
        }

        filterChain.doFilter(request, response);

    }

}
