package com.github.uum.web.config;

import com.github.uum.web.intercepter.LoginInterceptor;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @Author lgs
 * @Date 16-8-7 下午2:34
 */
@Configuration
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/public/**")
                .addResourceLocations("classpath:/public/");

        registry.addResourceHandler("**.html")
                .addResourceLocations("classpath:/html/");
    }
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(new LoginInterceptor());
        // 配置拦截的路径
        ir.addPathPatterns("/**");
        // 配置不拦截的路径
        ir.excludePathPatterns("/error");
        ir.excludePathPatterns("/public/**");
        ir.excludePathPatterns("/login.html");
        ir.excludePathPatterns("/account/login");


        // 还可以在这里注册其它的拦截器
        //registry.addInterceptor(new OtherInterceptor()).addPathPatterns("/**");
    }
    @Bean
    public CorsRegistry corsRegistry() {
        CorsRegistry corsRegistry = new CorsRegistry();
        corsRegistry.addMapping("/**")
                .allowedOrigins("github.com")
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowedHeaders("*");
        return corsRegistry;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        //        builder.propertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        //        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        //        builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }

 /*   @Bean
    public HttpControllerExceptionHandler controllerExceptionHandler(){
        return new HttpControllerExceptionHandler();
    }*/
}
