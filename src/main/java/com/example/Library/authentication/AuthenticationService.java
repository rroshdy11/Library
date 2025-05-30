package com.example.Library.authentication;

import com.example.Library.User.Role;
import com.example.Library.User.User;
import com.example.Library.config.JwtService;
import com.example.Library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword())) // Encrypting the password
                        .name(request.getName())
                        .phone(request.getPhone())
                        .role(Role.valueOf(request.getRole())) // Assuming Role is an enum
                        .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .message("User registered successfully")
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws ExecutionException, InterruptedException {

        System.out.println("Login request received for user: " + request.getUsername());
        System.out.println("Password provided: " + request.getPassword());

        // Load user
        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("User found: " + user.getUsername());
        // Validate password manually
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Generate token
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("success")
                .build();

    }
}
