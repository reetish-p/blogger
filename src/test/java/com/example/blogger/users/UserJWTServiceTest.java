package com.example.blogger.users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserJWTServiceTest {
    UserJwtService jwtService = new UserJwtService();

    @Test
    void createJWT_can_creat_JWT_from_username() {
        String jwtToken = jwtService.createJwtToken("reetish");
        assertNotNull(jwtToken);
    }

    @Test
    void decodeJWT_can_get_username_from_JWT() {
        String jwtToken = jwtService.createJwtToken("reetish");
        String username =  jwtService.getUsernameFromJwtToken(jwtToken);
        assertEquals("reetish", username);
    }
}
