package config.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigEx02 { // 이제부터 진짜 Spring Security 설정

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Throwable { // Proxy가 아님. Proxy로 하면 일일이 설정을 다 해줘야 함.
        // spring Security에서 HTTP 보안 필터 체인을 설정하는 클래스입니다. 이 필터 체인은 애플리케이션에 대한 HTTP 요청에 대해 필터링 및 보안을 적용합니다.
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web
                        .ignoring() // filter 안 만들겠다. 이쪽으로 오는 보안은 모두 무시하겠다.
                        .requestMatchers(new AntPathRequestMatcher("/assets/**"));
            }
        };
    }
}
