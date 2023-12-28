package org.choongang.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * 설정 무력화
     */
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.build();
    }
}