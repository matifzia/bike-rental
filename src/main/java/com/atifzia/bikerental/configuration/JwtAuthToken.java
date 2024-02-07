package com.atifzia.bikerental.configuration;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class JwtAuthToken extends AbstractAuthenticationToken {
    private final String token;
    private Object principal;

    public JwtAuthToken(String token) {
        super(null);
        this.token = token;
        setAuthenticated(false);
    }

    public JwtAuthToken(String token, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getToken() {
        return token;
    }

    public void setPrincipal(UserDetails userDetails) {
        this.principal = userDetails;
    }
}

