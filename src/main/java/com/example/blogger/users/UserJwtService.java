package com.example.blogger.users;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserJwtService {
    private static final String SECRET = "asdgahsjdasgdjhguqwygeiqwueqi23bbjdksjd";
    public static final String CLAIM_USERNAME = "username";
    Algorithm algorithm = Algorithm.HMAC256(SECRET);

    String createJwtToken(String username){
        return JWT.create()
                .withClaim(CLAIM_USERNAME,username)
                .withIssuedAt(new Date()) //TODO : add expiration
                .sign(algorithm);
    }

    String getUsernameFromJwtToken(String jwtToken)
    {
        return JWT.require(algorithm)
                .build()
                .verify(jwtToken)
                .getClaim(CLAIM_USERNAME)
                .asString();
    }


}
