package com.parkfinder.security;

import com.parkfinder.repository.UserRepository;
import com.parkfinder.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class AppSecurityConfig {

    private final UserRepository userRepository;

    private static final String[] AUTH_WHITELIST = {
            // Google Maps API endpoints.
            "/maps/api/**",

            // Application endpoints.
            "/",
            "/register",
            "/process_register",
            "/api/v1/**",
            "/search/**",
            "/directions/**",

            // MIME error fix.
            "/css/**",
            "/js/**",
            "/images/**"
    };

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> response.sendRedirect("/");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors().and().csrf().disable() // Disabling CSRF fixes 403 error
                .authorizeHttpRequests().requestMatchers("/configureMarkers/**").hasAuthority("ADMIN")
                .and().authorizeHttpRequests().requestMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated()
                .and().headers().cacheControl().disable() // Potential fix for MIME, above it's fixed already.
                .and().formLogin()
                .and().exceptionHandling().accessDeniedPage("/accessDenied")
                .and().logout().logoutSuccessHandler(logoutSuccessHandler())
                .and().build();
    }

}
