package com.example.blogger.users.dtos;

import lombok.Data;

import javax.validation.constraints.*;

public class UserDTO {
    private UserDTO(){
    }

    @Data
    public static class CreateUserRequest{
        @NotEmpty(message = "The username is required.")
        @Size(message = "Must be minimum 4 characters", min = 4)
        private String username;

        public String password;

        @NotEmpty(message = "The email address is required.")
        @Email(message = "Email is not valid.",regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
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
