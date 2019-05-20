package cn.irua.demo;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import cn.irua.demo.handler.AuthorizationInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport{
	@Resource
	private AuthorizationInterceptor authorizationInterceptor;
	@Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

	@Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);

        super.addInterceptors(registry);
    }
}
