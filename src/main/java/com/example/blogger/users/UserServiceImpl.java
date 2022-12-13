package com.example.blogger.users;

import com.example.blogger.users.dtos.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserJwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserJwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public  UserDTO.LoginUserResponse signUpUser(UserDTO.CreateUserRequest user){
        UserEntity userEntity = modelMapper.map(user,UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        UserDTO.LoginUserResponse response = modelMapper.map(savedUser,UserDTO.LoginUserResponse.class);
        response.setToken(jwtService.createJwtToken(response.getUsername()));
        return response;
    }

    public  UserDTO.LoginUserResponse loginUser(UserDTO.LoginUserRequest user){
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new UserNotFoundException(user.getUsername())
        );

        if(passwordEncoder.matches(user.getPassword(),userEntity.getPassword()))
        {
            UserDTO.LoginUserResponse response = modelMapper.map(userEntity,UserDTO.LoginUserResponse.class);
            response.setToken(jwtService.createJwtToken(response.getUsername()));
            return response;
        } else {
            throw new UserAuthenticationException();
        }
    }

    public UserDTO.GetUserResponse getUserByUsername(String username){
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
        return modelMapper.map(userEntity,UserDTO.GetUserResponse.class);
    }

    public UserEntity getUserEntityByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
    }

    public UserDTO.GetUserResponse followUser(String username, Integer loggedInUserUserId) {
        UserDTO.GetUserResponse userToFollow = getUserByUsername(username);
        userRepository.followUser(loggedInUserUserId, userToFollow.getId());
        return userToFollow;
    }

    public UserDTO.GetUserResponse unfollowUser(String username, Integer loggedInUserUserId) {
        UserDTO.GetUserResponse userToUnfollow = getUserByUsername(username);
        userRepository.unfollowUser(loggedInUserUserId, userToUnfollow.getId());
        return userToUnfollow;
    }

    static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(String username) {
            super("No such user found with username "+username);
        }
    }
    static class UserAuthenticationException extends SecurityException{
        public UserAuthenticationException() {
            super("Authentication Failed");
        }
    }
}
