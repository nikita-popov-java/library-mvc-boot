package com.nikitapopov.librarymvcboot.configurations;

import com.nikitapopov.librarymvcboot.services.LibraryUserDetailsService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final LibraryUserDetailsService libraryUserDetailsService;

    @Autowired
    public SecurityConfiguration(LibraryUserDetailsService libraryUserDetailsService) {
        this.libraryUserDetailsService = libraryUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(libraryUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/login","/auth/registration", "/error", "/books").permitAll()
                                .anyRequest().authenticated()
                ).formLogin(form ->
                        form.loginPage("/auth/login")
                                .loginProcessingUrl("/auth/login-process")
                                .defaultSuccessUrl("/people", true)
                                .failureUrl("/auth/login?error")
                );

        return security.build();
    }
}