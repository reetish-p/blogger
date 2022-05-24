package com.example.blogger.users;

import com.example.blogger.common.ErrorDTO;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    @PostMapping("")
    ResponseEntity<UserDTO.LoginUserResponse> signUpUser(@RequestBody UserDTO.CreateUserRequest request){
        var response = usersService.signUpUser(request);
        return ResponseEntity.created(
                URI.create("/users/"+response.getId())
        ).body(response);
    }

    @PostMapping("/login")
    ResponseEntity<UserDTO.LoginUserResponse> loginUser(
            @RequestBody UserDTO.LoginUserRequest request
    ){
        var response = usersService.loginUser(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/@{username}")
    ResponseEntity<UserDTO.GetUserResponse> getUser(
            @PathVariable("username") String username
    ){
        var response = usersService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler
    ResponseEntity<ErrorDTO> exceptionHandler(Exception e){
        if(e instanceof UsersService.UserNotFoundException)
            return ResponseEntity.status(404).body(new ErrorDTO(e.getMessage()));
        if(e instanceof UsersService.UserAuthenticationException)
            return ResponseEntity.status(401).body(new ErrorDTO(e.getMessage()));
        return ResponseEntity.status(500).body(new ErrorDTO(e.getMessage())); //Generic
    }
}
