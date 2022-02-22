package com.classpath.ordermicroservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@RequiredArgsConstructor
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    //private final UserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/login**", "/actuator/**", "/logout**", "/singin", "/singout", "/about-us**", "/h2-console/**"")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/orders/**")
                .hasAnyRole("Everyone", "super_admins", "admins")
                .antMatchers(HttpMethod.POST, "/api/v1/orders/**")
                .hasAnyRole("admins", "super_admins")
                .antMatchers(HttpMethod.DELETE, "/api/v1/orders/**")
                .hasRole("super_admins")
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();


    }

/*
                    .formLogin()
                .and()
                    .httpBasic();
*/


//    @Override
    //in-memory authentication
            /* authenticationManagerBuilder.inMemoryAuthentication()

                            .inMemoryAuthentication()
                                .withUser("kiran")
                                .password("$2a$10$yhNhO0I.bhXhe2.IctZOk.aZD5ry9wAhiWc6P1G7rGl0zGoZb0Etq")
                                .roles("USER")
                            .and()
                                .withUser("vinay")
                                .password("$2a$10$gJMRaxA7Rvz2CTpHy1L4kuMY5px3z.LYW.u9uI54jz9nguL0puU8G")
                                .roles("USER", "ADMIN")
                            .and()
                                .withUser("harish")
                                .password("$2a$10$yhNhO0I.bhXhe2.IctZOk.aZD5ry9wAhiWc6P1G7rGl0zGoZb0Etq")
                                .roles("SUPER_ADMIN")
                            .and()
                             .passwordEncoder(passwordEncoder());
            */

/*    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("groups");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}