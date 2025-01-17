package config.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigEx05 {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web
                    .ignoring()
                    .requestMatchers(new AntPathRequestMatcher("/assets/**"));
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin((formLogin) -> {
                    formLogin.loginPage("/user/login");
                })
                .authorizeHttpRequests((authorizeRequests) -> {
                    // ACL
                    authorizeRequests
                            .requestMatchers(new RegexRequestMatcher("^/board/?(write|delete|modify|reply).*$", null)) // ^, $ : 시작과 끝을 의미, (write|delete|modify|reply): 이것들 중 하나가 있든지 없든지...
                            // .hasAnyRole("ADMIN", "USER") // 권한 설정을 이와 같이 할 수 있음.
                            .authenticated() // 인증을 받아야 함을 의미.

                            .anyRequest() // 나머지 request는 모두 통과되도록
                            .permitAll();
                });
        return http.build();
    }
}
