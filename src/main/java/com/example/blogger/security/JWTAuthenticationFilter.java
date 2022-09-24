package com.example.blogger.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
public class JWTAuthenticationFilter extends AuthenticationFilter {

    public JWTAuthenticationFilter(JWTAuthManager manager, JWTAuthConverter converter) {
        super(manager, converter);
        this.setSuccessHandler((request,response,authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });
    }

}
