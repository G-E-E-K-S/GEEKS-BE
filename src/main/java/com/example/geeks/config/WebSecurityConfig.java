package com.example.geeks.config;

import com.example.geeks.Security.JwtFilter;
import com.example.geeks.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.io.IOException;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final MemberService memberService;

    @Value("${jwt.secret}")
    private String secretKey;

    private final CustomSessionExpiredStrategy customSessionExpiredStrategy;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    System.out.print("Success");
                })
                .deleteCookies("JSESSIONID", "token") // 로그아웃 후 삭제할 쿠키 지정
                .permitAll() // /logout 엔드포인트에 대한 접근은 모든 사용자에게 허용
                .and()
                .authorizeRequests()
                // 특정 API에 대해 모든 사용자에게 접근 허용
                .antMatchers("*").permitAll()
                .and()
                .addFilterBefore(new JwtFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class);
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf(csrf -> csrf.disable())
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    System.out.print("Success");
                })
                .deleteCookies("JSESSIONID", "token") // 로그아웃 후 삭제할 쿠키 지정
                .permitAll() // /logout 엔드포인트에 대한 접근은 모든 사용자에게 허용
                .and()
                .httpBasic().disable();

        http
                .sessionManagement()
                .sessionFixation().changeSessionId()
                .maximumSessions(1) // 최대 동시 세션 수를 1로 설정
                .expiredSessionStrategy(customSessionExpiredStrategy) // 세션이 만료된 경우 처리할 전략 설정
                .maxSessionsPreventsLogin(true) // 동시에 여러 세션을 허용하지 않도록 설정
                .sessionRegistry(sessionRegistry());
        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher(){
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
