package com.springSecEx.springSecEx;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private JwtUtil jwtUtil;

    private EmployeeRepository repository;
    private PasswordEncoder passwordEncoder;


    public AuthController(JwtUtil jwtUtil, EmployeeRepository repository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    private String login(@RequestBody Employee request) {
        Employee user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Step 2: check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Step 3: generate token
        return jwtUtil.generateToken(user.getUsername());

    }
    @PostMapping("/register")
    public String register(@RequestBody Employee emp) {

        emp.setPassword(passwordEncoder.encode(emp.getPassword()));
        repository.save(emp);

        return "User saved";
    }
}
