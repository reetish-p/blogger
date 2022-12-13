package com.example.blogger.security;

import com.example.blogger.users.UserEntity;
import com.example.blogger.users.UserJwtService;
import com.example.blogger.users.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class JWTAuthManager implements AuthenticationManager {
    private final UserJwtService userJwtService;
    private final UserServiceImpl userServiceImpl;

    public JWTAuthManager(UserJwtService userJwtService, UserServiceImpl userServiceImpl) {
        this.userJwtService = userJwtService;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(!(authentication instanceof JWTAuthentication jwtAuthentication))
            throw new IllegalArgumentException("Authentication is not supported");

        String token = jwtAuthentication.getCredentials();
        String username = userJwtService.getUsernameFromJwtToken(token);
        UserEntity user = userServiceImpl.getUserEntityByUsername(username);
        jwtAuthentication.setUserEntity(user);
        return jwtAuthentication;
    }
}
