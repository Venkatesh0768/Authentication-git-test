package org.gittest.backend.service;


import lombok.RequiredArgsConstructor;
import org.gittest.backend.dto.LoginRequest;
import org.gittest.backend.dto.RegisterRequest;
import org.gittest.backend.dto.UserDto;
import org.gittest.backend.model.User;
import org.gittest.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDto registerUser(RegisterRequest request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        UserDto userDto = UserDto.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();


        return userDto;
    }


    @Transactional
    public String loginUser(LoginRequest request) {
        User user = userRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equals(request.getEmail()) && u.getPassword().equals(request.getPassword()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        return "Login successful for user: " + user.getFullName();
    }


}
