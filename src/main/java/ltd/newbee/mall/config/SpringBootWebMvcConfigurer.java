package ltd.newbee.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Richard
 * @Date 2021/4/23 3:18 PM
 */
@Configuration
public class SpringBootWebMvcConfigurer implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:/Users/ellison/code/newbee-mall/src/main/resources/static/upload/");
    }
}
