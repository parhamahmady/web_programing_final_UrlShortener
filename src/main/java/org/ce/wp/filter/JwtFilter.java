package org.ce.wp.filter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ce.wp.config.SpringSecurityConfiguration;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.service.AAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";
    private final AntPathMatcher antPathMatcher = new AntPathMatcher("/");
    private final AAService aaService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String uri = request.getRequestURI().substring("shortener/".length());
            boolean isPermitted = Arrays.stream(SpringSecurityConfiguration.PERMITTED).anyMatch(
                    s -> antPathMatcher.match(s, uri));
            if (!isPermitted && !uri.equals("/")) {
                Optional<String> optional = Arrays.stream(request.getCookies())
                        .filter(cookie -> AUTH_HEADER.equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findAny();
                if (optional.isEmpty()) {
                    throw new CredentialsException("Invalid Credentials");
                }
                String header = optional.get();
                if (StringUtils.hasLength(header) && header.startsWith(TOKEN_PREFIX)) {
                    String token = header.substring(TOKEN_PREFIX.length());
                    UsernamePasswordAuthenticationToken authenticationToken = aaService.authenticateJwt(token);
                    authenticationToken.setDetails(new
                            WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } else {
                    throw new CredentialsException("Invalid Credentials");
                }
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            log.warn("", e);
            resolver.resolveException(request, response, null, e);
        }
    }
}
