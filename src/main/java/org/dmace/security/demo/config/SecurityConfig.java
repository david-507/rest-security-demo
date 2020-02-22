package org.dmace.security.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomBasicAuthEntryPoint basicAuthEntryPoint;
    private final UserDetailsService service;
    private final PasswordEncoder encoder;

    // Authentication
    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(encoder);
    }

    // Authorization
    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .httpBasic()
                .authenticationEntryPoint(basicAuthEntryPoint)
                .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/producto/**", "/lote/**").hasRole("USER")
                    .antMatchers(HttpMethod.POST, "/producto/**", "/lote/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/producto/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/producto/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/pedido/**").hasAnyRole("USER","ADMIN")
                    .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }


}
