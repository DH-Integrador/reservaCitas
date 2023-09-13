package com.integrador.ReservaCitas.config;

import org.springframework.context.annotation.*;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO validar segun url ROl

        http.authorizeRequests()
                .antMatchers("/turno").hasAnyRole("USER","ADMIN")
                .antMatchers("/odontologos/**","/pacientes/**").hasRole("ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .and()
                .httpBasic();

        /*      Para consumir desde el front
                .formLogin().and()
                .rememberMe().key("odsajndoasdoasjd")
                .and().logout().deleteCookies("JSESSIONID");
         */

        // para cuando se usa http basic
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
    }


    @Bean
    public DaoAuthenticationProvider authProvicer(UserDetailsService userDetailsService, PasswordEncoder encoder){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder(12);
    }
}
