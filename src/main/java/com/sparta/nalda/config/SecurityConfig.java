package com.sparta.nalda.config;

import com.sparta.nalda.filter.JWFilter;
import com.sparta.nalda.filter.LoginFilter;
import com.sparta.nalda.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final TokenProvider tokenProvider;

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable())
            .formLogin((auth) -> auth.disable())
            .httpBasic((httpBasic) -> httpBasic.disable())
            .authorizeHttpRequests((auth) ->
            auth.requestMatchers("/login", "/signup").permitAll()
                .requestMatchers("/owners").hasRole("OWNER")
                .anyRequest().authenticated()
        )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(new JWFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}