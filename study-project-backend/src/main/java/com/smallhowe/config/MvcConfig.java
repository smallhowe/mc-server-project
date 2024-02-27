package com.smallhowe.config;

import com.smallhowe.interceptor.AuthorizeInterceptor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${my.image.path}")
    private String imagePath;
    @Value("${my.file.path}")
    private String filePath;
    @Value("${spring.web.resources.static-locations}")
    private String webPath;
    @Resource
    private AuthorizeInterceptor authorizeInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (imagePath.equals("default") || imagePath.isEmpty()) {
            imagePath = System.getProperty("user.dir").replace("\\", "/") + "/static/images/";
        }else if (!imagePath.substring(imagePath.length()-1).equals("/")) {
            imagePath = imagePath + "/";
        }
        if (filePath.equals("default") || filePath.isEmpty()) {
            filePath = System.getProperty("user.dir").replace("\\", "/") + "/static/download/";
        }else if (!filePath.substring(filePath.length()-1).equals("/")) {
            filePath = filePath + "/";
        }

        System.out.println(webPath);
        registry.addResourceHandler("/").addResourceLocations(webPath + "index.html");
        registry.addResourceHandler("/assets/**").addResourceLocations(webPath + "assets/");
        registry.addResourceHandler("/img/**").addResourceLocations("file:"+imagePath);
        registry.addResourceHandler("/resource/download/**").addResourceLocations("file:"+filePath);
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
                .addPathPatterns("/**").excludePathPatterns("/*","/assets/**","/api/auth/**","/img/**");
    }
}
