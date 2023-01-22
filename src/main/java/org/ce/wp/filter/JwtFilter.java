package org.ce.wp.filter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ce.wp.config.SpringSecurityConfiguration;
import org.ce.wp.service.AAService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final AntPathMatcher antPathMatcher = new AntPathMatcher("/");
    private final AAService aaService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI().substring("alert-engine/".length());
        boolean isPermitted = Arrays.stream(SpringSecurityConfiguration.PERMITTED).anyMatch(
                s -> antPathMatcher.match(s, uri));
        if (!isPermitted) {
            String header = request.getHeader(AUTH_HEADER);
            if (StringUtils.hasLength(header) && header.startsWith(TOKEN_PREFIX)) {
                String token = header.substring(TOKEN_PREFIX.length());
                UsernamePasswordAuthenticationToken authenticationToken = aaService.authenticateJwt(token);
                authenticationToken.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } else {
                throw new ServletException("Invalid Credentials");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
