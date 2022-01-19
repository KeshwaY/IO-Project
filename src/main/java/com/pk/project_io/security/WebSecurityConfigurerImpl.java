package com.pk.project_io.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfigurerImpl(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    /*
        Role:
        - Admin
        - User
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                    .authenticationEntryPoint(getRestAuthenticationEntryPoint())
                    .and()
                .exceptionHandling()
                    .accessDeniedHandler(getCustomAccessDeniedHandler())
                    .and()
                .authorizeRequests()
                    .mvcMatchers("/api/v1/admin/**").hasRole("ADMINISTRATOR")
                    .mvcMatchers(HttpMethod.POST, "/api/v1/users/signup").anonymous()
                    .mvcMatchers("/api/v1/users/**").hasAnyRole("ADMINISTRATOR", "USER")
                    .mvcMatchers("/api/v1/posts/**").hasAnyRole("ADMINISTRATOR", "USER")
                    .mvcMatchers("/api/v1/comments/**").hasAnyRole("ADMINISTRATOR", "USER")
                    .mvcMatchers("/api/v1/groups/**").hasAnyRole("ADMINISTRATOR", "USER")
                    .mvcMatchers("/**").authenticated()
                    .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public RestAuthenticationEntryPoint getRestAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler getCustomAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}