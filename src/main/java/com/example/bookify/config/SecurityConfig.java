package com.example.bookify.config;

import com.example.bookify.repository.UserRepository;
import com.example.bookify.service.BookifyUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                // define which requests are allowed and which not
                        authorizeRequests().
                // everyone can download static resources (css, js, images)
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                // everyone can log in and register
                        antMatchers("/", "/users/login", "/users/register").permitAll().
                        antMatchers("/offers/all").permitAll().

                antMatchers("/offers/**", "/api/**", "/profile").permitAll().
                // all other pages are available for logged-in users
                        anyRequest().
                authenticated().
                and().
                // configuration of form login
                        formLogin().
                // the custom login form
                        loginPage("/users/login").
                // the name of the username form field
                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                // the name of the password form field
                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                // where to go in case that the login is successful
                        defaultSuccessUrl("/", true).
                // where to go in case that the login failed
                        failureForwardUrl("/users/login-error").
                and().
                // configure logout
                        logout().
                // which is the logout url, must be POST request
                        logoutUrl("/users/logout").
                // on logout go to the home page
                        logoutSuccessUrl("/").
                // invalidate the session and delete the cookies
                        invalidateHttpSession(true).
                deleteCookies("JSESSIONID");


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new BookifyUserDetailsService(userRepository);
    }
}