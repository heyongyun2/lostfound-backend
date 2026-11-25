package org.example.springboot.congfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 浏览器访问 /images/xxx  → 后端去 D:/lost_found_images/ 找图片
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:D:/lost_found_images/");
    }
}
