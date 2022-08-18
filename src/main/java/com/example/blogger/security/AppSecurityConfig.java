package com.example.blogger.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired JWTAuthManager jwtAuthManager;
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users","/users/login").permitAll()
                .antMatchers("/h2-console/**").permitAll() //to unblock H2-console
               // .antMatchers(HttpMethod.GET, "/articles").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
        http.headers().frameOptions().disable(); //to view frames/UI in H2-console
    }

    @Override
    protected JWTAuthManager authenticationManager() throws Exception {
        return jwtAuthManager;
    }
}
