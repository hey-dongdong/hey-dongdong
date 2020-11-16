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
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/user/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/sign-up").permitAll()
                .antMatchers(HttpMethod.POST, "/user/sign-in").permitAll()
                .antMatchers(HttpMethod.POST, "/user/find-info/**").permitAll()
                .antMatchers(HttpMethod.POST, "/menu/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/change-pw").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/user/no-show-count").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/user/sign-out").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/history/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/my-menu/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/order/add").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/order/get-progress").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/order/update-progress").permitAll()
                .antMatchers(HttpMethod.POST, "/admin/**").permitAll()
                .antMatchers(HttpMethod.GET, "/send/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

    }
}
