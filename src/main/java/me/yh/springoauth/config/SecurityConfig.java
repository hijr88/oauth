package me.yh.springoauth.config;

import lombok.RequiredArgsConstructor;
import me.yh.springoauth.oauth.CustomOAuth2UserService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/", "/static/**" ,"/css/**", "/images/**", "/js/**", "/h2-console/**", "/login").permitAll()
                .antMatchers("/api/v1/**").hasRole("USER")
                .anyRequest().authenticated()
            .and()
                .logout()
            .and()
                .oauth2Login()
                .loginPage("/login")
                .authorizationEndpoint(a -> a.baseUri("/login/oauth"))
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
