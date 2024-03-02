package org.choongang.configs;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.interceptors.CommonInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final CommonInterceptor commonInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor);
                //.addPathPatterns("/**");   // "/**" 모든경로 : 모든 경로일때는 따로 사용하지 않아도 가능
    }
}