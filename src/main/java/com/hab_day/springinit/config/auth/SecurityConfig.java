package com.hab_day.springinit.config.auth;

import com.hab_day.springinit.domain.user.Role;
import com.hab_day.springinit.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()//url별 관리 설정 옵션 시작범. antMatchers 사용 위해
                    .antMatchers("/", "/css/**", "/images/**",
                            "/js/**", "/h2-console/**").permitAll()//전체열람
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())//유저에게만 권한
                    .anyRequest().authenticated()//설정값 이외의 url은 인증되니 사용자들에게만 허용(로그인한 사용)
                .and()
                .csrf() // 추가
                .ignoringAntMatchers("/h2-console/**").disable() // 추가
                    .logout()
                        .logoutSuccessUrl("/")//로그아웃 성공시 /로 이동
                .and()
                    .oauth2Login()//OAuth2 로그인 기능 설정 진입점
                        .userInfoEndpoint()//사용자 정보 가져올 때 설정 담당
                            .userService(customOAuth2UserService);//로그인 성공 시 UserService 인터페이스 구현체 등록

    }
}
