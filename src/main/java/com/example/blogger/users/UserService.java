package com.example.blogger.users;

import com.example.blogger.users.dtos.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public  UserDTO.LoginUserResponse signUpUser(UserDTO.CreateUserRequest user);

    public  UserDTO.LoginUserResponse loginUser(UserDTO.LoginUserRequest user);

    public UserDTO.GetUserResponse getUserByUsername(String username);

    public UserEntity getUserEntityByUsername(String username);

    public UserDTO.GetUserResponse followUser(String username, Integer loggedInUserUserId);

    public UserDTO.GetUserResponse unfollowUser(String username, Integer loggedInUserUserId);

}
