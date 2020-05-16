package crom.accel.springboot.config.auth;

import crom.accel.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
   @EnableWebSecurity : Spring Security 설정을 활성화
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
           csrf().disable().headers().frameOptions().disable() : h2-console 사용을 위해 disable

            authorizeRequests() : URL별 권한 관리를 설정하는 옵션의 시작점. authorizeRequests 가 선언되어야만 antMatchers 옵션을 사용가능

            antMatchers()
            권한관리대상을 지정하는 옵션. URL, HTTP 메소드별로 관리가능
            "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었음
            "/api/v1/**" 주소를 가진 API는 USER권한을 가진 사람만 가능하도록 처리

            anyRequest() : 설정된 값 이외 나머지 URL
            authenticated() : 나머지 URL들은 모두 인증된 사용자들에게만 허용. 인증된 사용자=로그인한 사용자를 의미

            logout().logoutSuccessUrl("/") : 로그아웃기능에 대한 설정의 진입점. 로그아웃 성공시 "/"주소로 이동함
            oauth2Login() : OAuth2 로그인 기능에 대한 설정의 진입점
            userInfoEndpoint() : OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정

            userService() : 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 등록.
            리소스서버(소셜서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시

         */
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout().logoutSuccessUrl("/")
                .and()
                    .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);

    }
}
