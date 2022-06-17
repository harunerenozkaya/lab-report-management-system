package com.harun.labManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity()
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new LabUserDetailsService();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().and().authorizeRequests()

                //users endpoint can be accessed by only MANAGER ROLE
                .antMatchers("/users").hasRole("MANAGER")

                //user endpoint can be accessed by only MANAGER ROLE
                .antMatchers("/user").hasRole("MANAGER")

                //user endpoint can be accessed by only MANAGER ROLE
                .antMatchers("/addUser").hasRole("MANAGER")

                //user endpoint can be accessed by MANAGER and LABORANT ROLES
                .antMatchers("/reports").hasAnyRole("MANAGER", "LABORANT")

                //user endpoint can be accessed by MANAGER and LABORANT ROLES
                .antMatchers("/addReport").hasAnyRole("MANAGER", "LABORANT")

                //can can be accessed by all users
                .antMatchers("/").permitAll()

                .and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/reports",true)
                .failureUrl("/").and().logout().logoutUrl("/logout");


    }

}
