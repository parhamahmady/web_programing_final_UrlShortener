package org.ce.wp.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ce.wp.config.SpringSecurityConfiguration;
import org.ce.wp.dto.ExceptionDto;
import org.ce.wp.exception.AlertEngineInternalException;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidJwtToken;
import org.ce.wp.exception.InvalidUrlIdException;
import org.ce.wp.service.AAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.util.NestedServletException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
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
                    throw new CredentialsException("Invalid Credentials");
                }
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (NestedServletException e) {
            Throwable originalException = e.getCause();
            log.warn("", originalException);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(getStatus((Exception) originalException).value());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(), new ExceptionDto(originalException.getMessage()));
            response.flushBuffer();
        } catch (Exception e) {
            log.warn("", e);
            resolver.resolveException(request, response, null, e);
        }
    }

    private HttpStatus getStatus(Exception e) {
        if (e instanceof AlertEngineInternalException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (e instanceof CredentialsException) {
            return HttpStatus.FORBIDDEN;
        } else if (e instanceof InvalidJwtToken) {
            return HttpStatus.FORBIDDEN;
        } else if (e instanceof InvalidUrlIdException) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
