package com.oneamz.inventory.config;

import com.oneamz.inventory.config.JwtConfigurer;
import com.oneamz.inventory.util.JwtTokenProvider;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/public").permitAll()
                .antMatchers("/api/private").authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider()));
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return new JwtTokenProvider(key);
    }
}
