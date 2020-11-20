package com.ewha.heydongdong.infra.config;

import com.ewha.heydongdong.infra.jwt.JwtAuthenticationFilter;
import com.ewha.heydongdong.infra.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/user/sign-up").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/sign-in").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/find-info/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/refresh-tokens").permitAll()
                .antMatchers(HttpMethod.GET, "/user/check-email-token/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/order/update-progress").permitAll()
                .antMatchers(HttpMethod.PUT, "/admin/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/menu/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/user/change-pw").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/user/sign-out").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/user/no-show-count").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/history/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/my-menu/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/order/add").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/order/get-progress").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

    }
}
