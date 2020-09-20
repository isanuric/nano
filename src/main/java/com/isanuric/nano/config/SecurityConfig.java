package com.isanuric.nano.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    private final NanoUserDetailsService nanoUserDetailsService;

    public SecurityConfig(NanoUserDetailsService nanoUserDetailsService) {
        this.nanoUserDetailsService = nanoUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        logger.info("Start configure");
        httpSecurity
                .authorizeRequests()
                .antMatchers("artist/all").hasRole("USER")
                .and()
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        logger.info("configure: [{}]", authenticationManagerBuilder.isConfigured());
        authenticationManagerBuilder.userDetailsService(nanoUserDetailsService);
        logger.info("configure: [{}]", authenticationManagerBuilder.isConfigured());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
