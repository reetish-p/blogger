package com.example.blogger.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private UsersRepository usersRepository;
    private ModelMapper modelMapper;
    private UserJwtService jwtService;

    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, UserJwtService jwtService) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    /* Sign up */
    public  UserDTO.LoginUserResponse signUpUser(UserDTO.CreateUserRequest user){
        UserEntity userEntity = modelMapper.map(user,UserEntity.class);
        UserEntity savedUser = usersRepository.save(userEntity);
        UserDTO.LoginUserResponse response = modelMapper.map(savedUser,UserDTO.LoginUserResponse.class);
        response.setToken(jwtService.createJwtToken(response.getUsername()));
        return response;
    }
    /* Login up */
    public  UserDTO.LoginUserResponse loginUser(UserDTO.LoginUserRequest user){
        UserEntity userEntity = usersRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new UserNotFoundException(user.getUsername())
        );
        System.out.println("------");
        //Hashing
        if(userEntity.getPassword().equals(user.getPassword())) //TODO
        {
            UserDTO.LoginUserResponse response = modelMapper.map(userEntity,UserDTO.LoginUserResponse.class);
            response.setToken(jwtService.createJwtToken(response.getUsername()));
            return response;
        } else {
            throw new UserAuthenticationException();
        }
    }

    public UserDTO.GetUserResponse getUserByUsername(String username){
        UserEntity userEntity = usersRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
        return modelMapper.map(userEntity,UserDTO.GetUserResponse.class);
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
