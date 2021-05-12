package ltd.newbee.mall.config;

import ltd.newbee.mall.interceptor.AdminLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Richard
 * @Date 2021/4/22 1:59 PM
 */
@Configuration
public class NeeBeeMallWebMvcConfigurer implements WebMvcConfigurer {
    
    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以 /admin 为前缀的 url 路径（后台登陆拦截）
//        registry.addInterceptor(adminLoginInterceptor)
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin/login")
//                .excludePathPatterns("/admin/dist/**")
//                .excludePathPatterns("/admin/plugins/**");
    }
}
