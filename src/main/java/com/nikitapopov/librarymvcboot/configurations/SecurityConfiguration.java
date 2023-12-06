package com.nikitapopov.librarymvcboot.configurations;

import com.nikitapopov.librarymvcboot.models.enums.Authority;
import com.nikitapopov.librarymvcboot.services.LibraryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(libraryUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/people").hasAuthority(Authority.ROLE_ADMIN.toString())
                                .requestMatchers("/auth/login","/auth/registration", "/error").permitAll()
                                .anyRequest().hasAnyAuthority(Authority.ROLE_USER.toString(), Authority.ROLE_ADMIN.toString())
                ).formLogin(form ->
                        form.loginPage("/auth/login")
                                .loginProcessingUrl("/auth/login-process")
                                .defaultSuccessUrl("/books", true)
                                .failureUrl("/auth/login?error")
                ).logout(auth ->
                        auth.logoutUrl("/logout")
                                .logoutSuccessUrl("/auth/login?logout"));

        return security.build();
    }
}