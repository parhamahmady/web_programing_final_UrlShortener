package org.ce.wp.config;

import lombok.Setter;
import org.ce.wp.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    public static final String[] PERMITTED = {"/**.css", "/**.js", "/**.png", "/**.ico",
            "/swagger-ui*", "/swagger-ui/*", "/v3/api-docs/**", "/user/**", "/auth/**"};

    @Setter(onMethod = @__(@Autowired))
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain authorizationFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(PERMITTED).permitAll();
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
