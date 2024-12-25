package com.lms.libmanage.service;


import com.lms.libmanage.entity.Token;
import com.lms.libmanage.entity.user.*;
import com.lms.libmanage.enums.Role;
import com.lms.libmanage.enums.TokenType;
import com.lms.libmanage.repository.TokenRepository;
import com.lms.libmanage.repository.UserRepository;
import com.lms.libmanage.utils.DuplicateEntryException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TokenRepository tokenRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public User getUserByUsername(String username) {
        var user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    public AuthenticationResponse register(RegisterRequest registerRequest, String roleName) {

        var role = Role.valueOf(roleName);
        var user = new User(registerRequest);
        checkIfUserUnique(registerRequest.getUsername(),registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(role);
        var savedUser = userRepository.save(user);
        Map<String,Object> claimMap= new HashMap<>();
        claimMap.put("Role",roleName);
        claimMap.put("Uuid",user.getUuid());
        var jwtToken = jwtService.generateToken(claimMap,user);
        var refreshToken = jwtService.generateRefreshToken(claimMap, user);


        saveToken(savedUser, jwtToken);

        return  new AuthenticationResponse(jwtToken,refreshToken,new UserResponse(savedUser));
    }


    private void saveToken(User savedUser, String jwtToken) {
        var token = new Token(jwtToken, TokenType.BEARER,false,false, savedUser);
        tokenRepository.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticationCredentials authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()));
        var user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow();
        var role = user.getRole();
        Map<String,Object> claimMap= new HashMap<>();
        claimMap.put("Role",role.name());
        claimMap.put("Uuid",user.getUuid());
        var jwtToken = jwtService.generateToken(claimMap,user);
        var refreshToken = jwtService.generateRefreshToken(claimMap,user);
        revokeAllUserTokens(user);


        saveToken(user, jwtToken);
        return  new AuthenticationResponse(jwtToken,refreshToken,new UserResponse(user));
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);

        if(username!=null) {
            var userDetails = this.userRepository.findByUsername(username).orElseThrow();
            var role = userDetails.getRole();
            Map<String,Object> claimMap= new HashMap<>();
            claimMap.put("Role",role.name());
            claimMap.put("Uuid", userDetails.getUuid());
            if(jwtService.isTokenValid(refreshToken,userDetails)) {
                var accessToken = jwtService.generateToken(claimMap,userDetails);
                revokeAllUserTokens(userDetails);
                saveToken(userDetails, accessToken);
                var authResponse = new AuthenticationResponse(accessToken,refreshToken,new UserResponse(userDetails));
                return authResponse;
            }
        }

        return null;
    }

    public ValidationResponse validateToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        token = authHeader.substring(7);

        var response = new ValidationResponse();
        response.setUsername(jwtService.extractUsername(token));
        response.setRole(jwtService.extractRole(token));
        response.setUuid(jwtService.extractUuid(token));

        return  response;
    }

    public AuthenticationResponse updateUser(UserUpdateRequest userUpdateRequest) {

        var user = userRepository.findById(userUpdateRequest.getId()).orElseThrow();
        if(!user.getEmail().equals(userUpdateRequest.getEmail()))
            checkIfUserUnique(null,userUpdateRequest.getEmail());

        if(!user.getUsername().equals(userUpdateRequest.getUsername()))
            checkIfUserUnique(userUpdateRequest.getUsername(),null);

        if(userUpdateRequest.getPassword()!=null && !userUpdateRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        }


        user.setFirstname(userUpdateRequest.getFirstname());
        user.setLastname(userUpdateRequest.getLastname());
        user.setEmail(userUpdateRequest.getEmail());
        user.setUsername(userUpdateRequest.getUsername());
        user.setAddress(userUpdateRequest.getAddress());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());

        var savedUser = userRepository.save(user);
        Map<String,Object> claimMap= new HashMap<>();
        claimMap.put("Role",user.getRole().name());
        claimMap.put("Uuid",user.getUuid());
        var jwtToken = jwtService.generateToken(claimMap,user);
        var refreshToken = jwtService.generateRefreshToken(claimMap, user);
        revokeAllUserTokens(user);
        saveToken(savedUser, jwtToken);
        return  new AuthenticationResponse(jwtToken,refreshToken,new UserResponse(savedUser));
    }

    private  void checkIfUserUnique(String username, String email) {
        if(username!=null)
            userRepository.findByUsername(username)
                    .ifPresent(u-> {throw new DuplicateEntryException("Username " + username + " is already taken.");});

        if(email!=null)
            userRepository.findByEmail(email)
                    .ifPresent(u-> {throw new DuplicateEntryException("An account using that email address already exists.");});
    }

    public String getTokenFromUUID(String UUID) {
        return tokenRepository.findAll().stream()
                .filter(x -> !x.isExpired() && !x.isRevoked() && x.getUser().getUuid().equals(UUID))
                .findFirst().orElseThrow().getToken();
    }
}
