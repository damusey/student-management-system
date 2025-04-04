package com.example.student.management.system.controller;

import com.example.student.management.system.dto.LoginRequest;
import com.example.student.management.system.security.CustomUserDetailsService;
import com.example.student.management.system.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AdminAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AdminAuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/admin-login")
    public ResponseEntity<String> adminLogin(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword());

        authenticationManager.authenticate(authenticationToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        String jwtToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(jwtToken);
    }
}
