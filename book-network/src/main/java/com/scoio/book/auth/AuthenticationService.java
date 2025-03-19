package com.scoio.book.auth;

import com.scoio.book.role.RoleRepository;
import com.scoio.book.user.UserRepository;
import com.scoio.book.user.Users;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public void register(@Valid RegistrationRequest request) {
        var userRole = roleRepository.findByName("USER").orElseThrow(()-> new IllegalStateException("Role user was not initialized")); //Soon better exception
    var user = Users.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .accountLocked(false)
            .enabled(false)
            .roles(List.of(userRole))
            .build();

    userRepository.save(user);
    sendValidationEmail(user);
    }

    private void sendValidationEmail(Users user) {
        var newToken = generateAndSaveActivationToken
    }
}
