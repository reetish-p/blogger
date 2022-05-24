package com.example.blogger.users;

import lombok.Data;

public class UserDTO {
    private UserDTO(){

    }

    @Data
    public static class CreateUserRequest{
        private String username;
        public String password;
        private String email;
        public String bio;
    }

    @Data
    public static class LoginUserRequest{
        private String username;
        public String password;
    }

    @Data
    public static class LoginUserResponse{
        private Integer id;
        private String username;
        private String email;
        public String bio;
        public String token;
    }

    @Data
    public static class GetUserResponse{
        private Integer id;
        private String username;
        public String bio;
    }
}
