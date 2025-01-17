package config.app;

import config.WebConfig;
import jakarta.servlet.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={WebConfig.class, SecurityConfigEx02.class})
@WebAppConfiguration
public class SecurityConfigEx02Test {
    private MockMvc mvc;
    private FilterChainProxy filterChainProxy;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        filterChainProxy = (FilterChainProxy)context.getBean("springSecurityFilterChain", Filter.class);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(new DelegatingFilterProxy(filterChainProxy), "/*")
                .build();
    }

    @Test
    public void testSecurityFilterChains() {
        List<SecurityFilterChain> securityFilterChains = filterChainProxy.getFilterChains();
        assertEquals(2, securityFilterChains.size());
    }

    @Test
    public void testSecurityFilterChain01() {
        SecurityFilterChain securityFilterChain = filterChainProxy.getFilterChains().getFirst();
        assertEquals(0, securityFilterChain.getFilters().size());
    }

    @Test
    public void testSecurityFilterChain02() {
        SecurityFilterChain securityFilterChain = filterChainProxy.getFilterChains().getLast();
        List<Filter> filters = securityFilterChain.getFilters();

        assertEquals(10, filters.size()); // 버전마다 개수가 다름.

        for (Filter filter : filters) {
            System.out.println(filter.getClass().getSimpleName());
        }
    }

    @Test
    public void testAssets() throws Throwable {
        mvc
                .perform(get("/assets/images/logo.svg"))          // 1. GET 요청을 "/assets/images/logo.svg" 경로로 보냄
                .andExpect(status().isOk())                       // 2. 응답 상태 코드가 200 OK여야 함
                .andExpect(content().contentType("image/svg+xml")) // 3. 응답의 콘텐츠 타입이 "image/svg+xml"이어야 함
                .andDo(print());                                  // 4. 응답 내용 출력 (디버깅 용)
    }

    @Test
    public void testPing() throws Throwable {
        mvc
                .perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("pong"))
                .andDo(print());
    }
}
