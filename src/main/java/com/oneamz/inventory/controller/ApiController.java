package com.oneamz.inventory.controller;

import com.oneamz.inventory.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public endpoint, everyone can access this.";
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "Private endpoint, only authenticated users can access this.";
    }

    @GetMapping("/generate-token")
    public String generateToken() {
        String token = jwtTokenProvider.createToken("exampleUser");
        return "Generated Token: " + token;
    }
}
