package com.example.springboot2essentials.configuration;

import com.example.springboot2essentials.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@SuppressWarnings("java:S5344")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomUserService customUserService;

  @Value("${security.default-password}")
  private String defaultPassword;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
//        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
        .authorizeRequests()
        .antMatchers("/animes/admin/**").hasRole("ADMIN")
        .antMatchers("/animes/**").hasRole("USER")
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .and()
        .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    auth.inMemoryAuthentication()
        .withUser("admin")
        .password(delegatingPasswordEncoder.encode(defaultPassword))
        .roles("USER", "ADMIN")
        .and()
        .withUser("user")
        .password(delegatingPasswordEncoder.encode(defaultPassword))
        .roles("USER");

    auth.userDetailsService(customUserService)
        .passwordEncoder(delegatingPasswordEncoder);
  }
}
