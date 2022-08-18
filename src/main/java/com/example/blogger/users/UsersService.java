package com.example.blogger.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private UsersRepository usersRepository;
    private ModelMapper modelMapper;
    private UserJwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, UserJwtService jwtService) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    /* Sign up */
    public  UserDTO.LoginUserResponse signUpUser(UserDTO.CreateUserRequest user){
        //TODO: validate invalid inputs
        UserEntity userEntity = modelMapper.map(user,UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
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
        //TODO: match password using Hashing
        //if(userEntity.getPassword().equals(user.getPassword()))
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
        UserEntity userEntity = usersRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
        return modelMapper.map(userEntity,UserDTO.GetUserResponse.class);
    }

    public UserEntity getUserEntityByUsername(String username){
        UserEntity userEntity = usersRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
        return userEntity;
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
