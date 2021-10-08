package com.example.security2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/member/**").authenticated()
                .antMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/").permitAll()
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginAction")
                .defaultSuccessUrl("/main")
            .and()
                .logout()
                .logoutUrl("logoutAction")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
            .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
            .and()
                .userDetailsService(userDetailsService);


//        http.authorizeRequests().antMatchers("/").permitAll();
//        http.authorizeRequests().antMatchers("/main").authenticated();
//        http.authorizeRequests().antMatchers("/member/**").authenticated();
//        http.authorizeRequests().antMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN");
//        http.authorizeRequests().antMatchers("/admin/**").hasAnyRole("ADMIN");
//
//
//        http.formLogin().loginPage("/login").defaultSuccessUrl("/main");
//        http.formLogin().loginProcessingUrl("/loginAction").defaultSuccessUrl("/main");
//        http.exceptionHandling().accessDeniedPage("/accessDenied");
//        http.userDetailsService(userDetailsService);
    }


}
