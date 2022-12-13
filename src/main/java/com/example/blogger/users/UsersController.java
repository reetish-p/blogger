package com.example.blogger.users;

import com.example.blogger.common.ErrorDTO;
import com.example.blogger.users.dtos.UserDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserServiceImpl userServiceImpl;

    public UsersController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("")
    ResponseEntity<UserDTO.LoginUserResponse> signUpUser(@Valid @RequestBody UserDTO.CreateUserRequest request){
        var response = userServiceImpl.signUpUser(request);
        return ResponseEntity.created(
                URI.create("/users/"+response.getId())
        ).body(response);
    }

    @PostMapping("/login")
    ResponseEntity<UserDTO.LoginUserResponse> loginUser(
            @RequestBody UserDTO.LoginUserRequest request
    ){
        var response = userServiceImpl.loginUser(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{username}")
    ResponseEntity<UserDTO.GetUserResponse> getUser(
            @PathVariable("username") String username
    ){
        var response = userServiceImpl.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{username}/follow")
    ResponseEntity<UserDTO.GetUserResponse> followUser(
            @PathVariable("username") String username,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        UserDTO.GetUserResponse response = userServiceImpl.followUser(username, userEntity.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{username}/follow")
    ResponseEntity<UserDTO.GetUserResponse> unfollowUser(
            @PathVariable("username") String username,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        UserDTO.GetUserResponse response = userServiceImpl.unfollowUser(username, userEntity.getId());
        return ResponseEntity.ok(response);
    }


    @ExceptionHandler
    ResponseEntity<ErrorDTO> exceptionHandler(Exception e){
        if(e instanceof UserServiceImpl.UserNotFoundException)
            return ResponseEntity.status(404).body(new ErrorDTO(e.getMessage()));
        if(e instanceof UserServiceImpl.UserAuthenticationException)
            return ResponseEntity.status(401).body(new ErrorDTO(e.getMessage()));
        if(e instanceof MethodArgumentNotValidException){
            List<String> errors = ((MethodArgumentNotValidException) e).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            for(String s: errors){
                sb.append(s);
                sb.append(" ");
            }
            return ResponseEntity.status(400).body(new ErrorDTO(sb.toString()));
        }
        return ResponseEntity.status(500).body(new ErrorDTO(e.getMessage())); //Generic
    }
}
