package com.bank.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenConfig tokenConfig;

    public SecurityConfig(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // allow H2 console frames
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()   // <-- allow H2 console
                .requestMatchers("/api/accounts/admin/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers("/api/accounts/user/**").hasAnyAuthority("SCOPE_USER","SCOPE_ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .opaqueToken(opaque -> opaque.introspector(tokenConfig.introspector()))
            );
        return http.build();
    }
}
