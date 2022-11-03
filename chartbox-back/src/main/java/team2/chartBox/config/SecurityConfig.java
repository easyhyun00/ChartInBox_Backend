package team2.chartBox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
public class SecurityConfig {

    @Bean // 비밀번호 암호화
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증필요
                .anyRequest().permitAll(); // 그 외 요청 모두 허용
                //.and()
                //.logout()// 로그아웃
                //.logoutUrl("/logout")
                // .logoutSuccessUrl("/")
                //.deleteCookies("JSESSIONID")
                //.logoutSuccessHandler(((request, response, authentication) ->
                //        response.setHeader("Location","http://localhost:3000")));
        return http.build();
    }
}
