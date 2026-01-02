package org.gittest.backend.controller;

import lombok.RequiredArgsConstructor;
import org.gittest.backend.dto.LoginRequest;
import org.gittest.backend.dto.RegisterRequest;
import org.gittest.backend.dto.UserDto;
import org.gittest.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegisterRequest request) {
        UserDto response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {
        String response = userService.loginUser(request);
        return ResponseEntity.ok().body(response);
    }

}
