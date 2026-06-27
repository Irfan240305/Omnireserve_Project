package com.omnireserve.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.omnireserve.dto.LoginRequest;
import com.omnireserve.config.JwtUtil;
import com.omnireserve.dto.AuthResponse;
import com.omnireserve.dto.RegisterRequest;
import com.omnireserve.entity.User;
import com.omnireserve.entity.UserRole;
import com.omnireserve.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;

  public AuthResponse register(RegisterRequest request) {
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(UserRole.USER);

    userRepository.save(user);

    String token = jwtUtil.generateToken(user.getEmail());
    return new AuthResponse(token);
    
  }

  public AuthResponse login(LoginRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    String token = jwtUtil.generateToken(request.getEmail());
    return new AuthResponse(token);
  }

}
