package com.notice.board;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    @Value("${upload.path}")        // yml에 저장되어 있는 값 사용
    private String connectPath;
        // upload.path = /upload/**
    @Value("${resource.path}")      // yml에 저장되어 있는 값 사용
    private String resourcePath;
        // resource.path = file:///c:/Temp/spring/project/



    @Override       // 외부파일 경로를 handler로 인정해주는 코드
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectPath)            // html에서 불러쓰는 경로
                .addResourceLocations(resourcePath);        // 외부 파일 저장 경로
    }

}
