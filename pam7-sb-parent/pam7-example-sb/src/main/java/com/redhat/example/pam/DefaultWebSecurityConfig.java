package com.redhat.example.pam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration("kieServerSecurity")
@EnableWebSecurity
public class DefaultWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String KIE_SERVER_PASSWORD = "password";
    private static final String KIE_SERVER_ROLE = "kie-server";
    private static final String ADMIN_ROLE = "Administrators";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/pam7-services/*")
                .authenticated()
                .and()
                .httpBasic();

        http.authorizeRequests().antMatchers("/console/**").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    @Override
    public UserDetailsManager userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        manager.createUser(User.withUsername("kieserver").password(encoder.encode(KIE_SERVER_PASSWORD)).roles(KIE_SERVER_ROLE).build());
        manager.createUser(User.withUsername("Claudio").password(encoder.encode(KIE_SERVER_PASSWORD)).roles(KIE_SERVER_ROLE, ADMIN_ROLE).build());
        return manager;
    }
}
