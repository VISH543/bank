package com.bank.security;



import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

@Configuration
public class TokenConfig {
    @Bean
    public OpaqueTokenIntrospector introspector() {
        return token -> {
            if ("admin-token".equals(token)) {
                return new OAuth2AuthenticatedPrincipal() {
                    public Map<String,Object> getAttributes() { return Map.of("scope","ADMIN"); }
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return List.of(new SimpleGrantedAuthority("SCOPE_ADMIN"));
                    }
                    public String getName() { return "admin"; }
                };
            }
            if ("user-token".equals(token)) {
                return new OAuth2AuthenticatedPrincipal() {
                    public Map<String,Object> getAttributes() { return Map.of("scope","USER"); }
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return List.of(new SimpleGrantedAuthority("SCOPE_USER"));
                    }
                    public String getName() { return "user"; }
                };
            }
            throw new RuntimeException("Invalid token");
        };
    }
}

