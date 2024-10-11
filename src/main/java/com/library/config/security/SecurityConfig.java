package com.library.config.security;

import com.library.jwt.JwtTokenFilter;
import com.library.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity()
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder;
    }

    @Bean
    AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
        return http.httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(
                        authorizeHttpRequest -> authorizeHttpRequest
                                .requestMatchers("/auth/signin",
                                        "/v1/api/users/register",
                                        "v1/api/users/register-admin"
                                        ,"/auth/refresh/**"
                                        ).permitAll()
                                .requestMatchers(HttpMethod.POST, "/v1/api/books/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/v1/api/books/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/v1/api/books/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/v1/api/books/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/v1/api/loans/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/v1/api/loans/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/v1/api/loans/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/v1/api/loans/**").hasRole("ADMIN")
                                .requestMatchers("v1/users/**").hasAnyRole( "USER", "ADMIN")
                                .anyRequest().permitAll()
                )
                .cors(cors -> {})
                .build();
    }

}