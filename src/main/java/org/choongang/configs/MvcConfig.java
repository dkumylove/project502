package org.choongang.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 설정클래스임을 알려줌
@EnableJpaAuditing
@EnableScheduling
@EnableConfigurationProperties(FileProperties.class)
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private FileProperties fileProperties;

//    /**
//     * 페이지 유지 기능 : 간단하게 페이지 연동할떄 사용
//     * 페이지에 따로 컨드롤러가 필요없는 간단한 상황
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("front/main/index");
//    }


    /**
     * 정적 경로 설정
     * 정적자원에 바로 접근하게하는
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileProperties.getUrl() + "**")
                .addResourceLocations("file:///" + fileProperties.getPath());

        // 정적 경로
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/"); // css,js등의 파일들접근경로
    }

    /**
     * 메세지 설정
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setUseCodeAsDefaultMessage(true);  // 메세지코드x -> 코드 그 상태로 출력
        ms.setBasenames("messages.commons", "messages.validations", "messages.errors");

        return ms;
    }

    /**
     * 일반form에서 patch,put,delete 사용가능하게 해줌
     * @return
     */
    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}