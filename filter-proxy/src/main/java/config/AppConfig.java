package config;

import filter.RealFilter;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Filter realFilter() { // DelegatingFilterProxy(MvcWebApplicationInitializer 내 getServletFilters 함수에 선언됨)에 선언된 bean name과 함수명이 일치해야 함. 일치하지 않으면 setting이 되지 않음.
        return new RealFilter();
    }
}
