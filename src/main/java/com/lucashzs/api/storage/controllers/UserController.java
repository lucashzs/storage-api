package com.lucashzs.api.storage.controllers;

import com.lucashzs.api.storage.dtos.LoginUserDto;
import com.lucashzs.api.storage.dtos.RegisterUserDto;
import com.lucashzs.api.storage.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register (@RequestBody @Valid RegisterUserDto registerUserDto){
        return this.authenticationService.register(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login (@RequestBody @Valid LoginUserDto loginUserDto){
        return this.authenticationService.login(loginUserDto);
    }
}
