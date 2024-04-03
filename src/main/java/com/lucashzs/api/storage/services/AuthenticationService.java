package com.lucashzs.api.storage.services;

import com.lucashzs.api.storage.dtos.LoginUserDto;
import com.lucashzs.api.storage.dtos.RegisterUserDto;
import com.lucashzs.api.storage.entities.User;
import com.lucashzs.api.storage.errors.exceptions.BadRequestException;
import com.lucashzs.api.storage.errors.exceptions.UnauthorizedException;
import com.lucashzs.api.storage.repositories.UserRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Modifying
    public ResponseEntity<Object> register(RegisterUserDto registerUserDto) {
        User newUser;

        if (registerUserDto.password().equals(registerUserDto.confirmPassword())) {
            try {
                validate(registerUserDto.password());
            } catch (Exception exception) {
                throw new BadRequestException(exception.getMessage());
            }
            if (this.userRepository.findByEmail(registerUserDto.email()).isPresent())
                throw new BadRequestException("Email already exists!");

            newUser = new User(registerUserDto, passwordEncoder.encode(registerUserDto.password()));
        } else {
            throw new BadRequestException("Passwords do not match!");
        }

        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created User Successfully!");
    }

    @Transactional
    public ResponseEntity<Object> login(LoginUserDto loginUserDto) {

        var user = this.userRepository.findByEmail(loginUserDto.email()).orElseThrow(() -> new BadRequestException("The data's entered is invalid!"));

        if (!new BCryptPasswordEncoder().matches(loginUserDto.password(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password!");
        }

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                loginUserDto.email(), loginUserDto.password());

        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationResponse);
        SecurityContextHolder.setContext(securityContext);

        return ResponseEntity.ok().body("Login User Successfully!");
    }

    public static void validate(String password) throws Exception {
        String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new Exception("The password must have a minimum of 8 characters, 1 uppercase letter, 1 lowercase letter and must not contain blanks!");
        }
    }

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
