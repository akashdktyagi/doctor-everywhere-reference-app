package com.automationfraternity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/swagger-ui/*").permitAll()
                .mvcMatchers(HttpMethod.GET,"/v3/api-docs").permitAll()
                .mvcMatchers(HttpMethod.GET,"/v3/api-docs/*").permitAll()
                .mvcMatchers(HttpMethod.GET,"/patient/*").permitAll()
                .mvcMatchers(HttpMethod.GET,"/patient").permitAll()
                .mvcMatchers(HttpMethod.POST,"/init_db").permitAll()

                .mvcMatchers(HttpMethod.POST,"/patient").authenticated()
                .mvcMatchers(HttpMethod.DELETE,"/patient/*").authenticated()
                .mvcMatchers(HttpMethod.PUT,"/patient/*").authenticated()
//                .anyRequest().authenticated()
                .and()
               .httpBasic();
    }

    @Autowired
    public void config(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("akash")
                .password("{noop}akash")
                .roles("ADMIN")
        ;
        auth.inMemoryAuthentication()
                .withUser("staff")
                .password("{noop}staff")
                .roles("STAFF");
    }
}