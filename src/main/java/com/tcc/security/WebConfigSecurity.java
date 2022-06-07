package com.tcc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity 
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Configura as solicitações de acesso por HTTP 
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
        .authorizeRequests() //permitir restringir acessos.
        .antMatchers("/").access("hasAnyAuthority('USER', 'ADMIN')")
        .antMatchers("/index").access("hasAnyAuthority('USER', 'ADMIN')")
        .antMatchers("/h2/**").permitAll()
        .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
        .and()
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login").permitAll();
        
    }

    //Cria autenticação do usuario com o SGBD
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        auth.inMemoryAuthentication()
        .withUser("admin").password(passwordEncoder().encode("1234"))
        .authorities("ADMIN")
        .and()
        .withUser("tasso")
        .password(passwordEncoder().encode("0000")).authorities("USER");
    }

    //Ignora URLs especificas
    @Override
    public void configure(WebSecurity web) throws Exception {
        
        web.ignoring().antMatchers("/templates/**", "/css/**", "/img/**");
        
    }    
}
