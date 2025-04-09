package com.scoio.book.auth;

import com.scoio.book.email.EmailService;
import com.scoio.book.email.EmailTemplateName;
import com.scoio.book.role.RoleRepository;
import com.scoio.book.user.TokenRepository;
import com.scoio.book.user.Tokens;
import com.scoio.book.user.UserRepository;
import com.scoio.book.user.Users;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    @Value("${activation-url}")
    private String activationUrl;


    public void register(@Valid RegistrationRequest request) throws MessagingException {
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

    private void sendValidationEmail(Users user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),"solomonchamamme@gmail.com",
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(Users user) {

        String generatedToken = generateActivationToken(6);
        var token = Tokens.builder().token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user).build();

        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationToken(int length) {
        String chatacters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i =0; i < length; i++){
            int randomIndex = secureRandom.nextInt(chatacters.length());
            codeBuilder.append(chatacters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
