package com.nikitapopov.librarymvcboot.configurations;

import com.nikitapopov.librarymvcboot.services.LibraryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;

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

    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests((requests) ->
                        requests.requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin((form) ->
                        form.loginPage("/login").permitAll()
                                .successForwardUrl("/people").permitAll()
                )
                .logout((logout) ->
                        logout.permitAll().disable()
                );
    }

}
