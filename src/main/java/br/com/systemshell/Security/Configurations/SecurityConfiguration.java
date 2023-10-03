package br.com.systemshell.Security.Configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CloudflareIPRangeFetcher wafConfig;

    private final List<String> publicEndpoints = Arrays.asList("/auth/**", "/v3/**", "/swagger-ui/**");
    private final List<String> allowedProxies = wafConfig.getIPRanges();
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                registry ->
                {
                    for (String endpoint : publicEndpoints) {
                        registry.requestMatchers(endpoint).permitAll();
                    }

                    for (String ipRange : allowedProxies) {
                        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(ipRange);
                        registry.requestMatchers(ipAddressMatcher).permitAll();
                    }

                    registry.anyRequest().authenticated();
                });

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}

