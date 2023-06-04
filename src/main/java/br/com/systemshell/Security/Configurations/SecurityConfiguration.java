package br.com.systemshell.Security.Configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private final List<String> allowedEndpoints = Arrays.asList("/auth/**", "/v3/**", "/swagger-ui/**");
    private final List<String> allowedCloudflareIpBlocks = CloudflareIPRangeFetcherServiceSingleton.getInstance().getIPRanges();
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        for (String endpoint : allowedEndpoints) {
            registry.requestMatchers(endpoint).permitAll();
        }
        /* Just unlock this block for block any request who isnt cloudflare, will not work if you doesnt use cloudflare in the frontend
        for (String ipRange : allowedCloudflareIpBlocks) {
            IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(ipRange);
            registry.requestMatchers(ipAddressMatcher).permitAll();
        }
        */

        registry.anyRequest().authenticated();

        return registry
                .and()
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

