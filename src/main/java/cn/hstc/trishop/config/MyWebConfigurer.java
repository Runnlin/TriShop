package cn.hstc.trishop.config;

import cn.hstc.trishop.interceptor.LoginInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 当访问某 URL 时，会现在这判断是否需要拦截，如果需要才使用相应的拦截器
 * 在拦截器中还有二次判断
 */
@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 所有请求都允许跨域
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    @Bean
    public LoginInterceptor getLoginIntercepter() {
        return new LoginInterceptor();
    }

    // 对所有路径应用拦截器，除了index
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html");
    }

    // 添加自定义Resolver
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
//        //添加消息转换器
//        converters.add(new MappingJackson2CborHttpMessageConverter());
//        //消息转换器与resolver绑定
//        resolvers.add(new MyBodyResolver(converters));
//    }
}
