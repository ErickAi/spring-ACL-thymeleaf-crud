package com.er.acl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/h2-console/**", "/favicon.ico",
                        "/resources/**", "/static/**", "/images/**", "/css/**", "/js/**"
                );
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {     //@formatter:off
        http
          .csrf().disable()
          .authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .anyRequest().authenticated()
//            .anyRequest().permitAll()
          .and()
          .formLogin()
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/")
          .and()
          .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
        ;
    }                                                               //@formatter:on

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("password").roles("USER", "EDITOR")
                .and()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("guest").password("password").roles("GUEST")
                .and()
                .withUser("someone").password("password").roles("SOMEONE");
    }
}
