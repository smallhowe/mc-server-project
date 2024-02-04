package com.smallhowe.config;

import com.smallhowe.interceptor.AuthorizeInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private AuthorizeInterceptor authorizeInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/resource/download/**").addResourceLocations("classpath:/static/download/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 设置允许的跨域请求来源为所有来源（通配符 *）
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizeInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/api/auth/**","/img/**","/resource/download/**");
    }
}
