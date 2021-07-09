package com.example.http.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置内容裁决的一些选项
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    }

    /**
     * 默认静态资源处理器
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO
//        super.addInterceptors(registry);
    }

    /**
     * 静态资源处理
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/static/");
//                 路径尾部的 '/' 不可省略
//                .addResourceLocations("classpath:/static/views/");
    }

    /**
     * 解决跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/requestMatch/*")
//                .allowedOrigins("https://domain1.com")
//                .allowedMethods("GET", "PUT", "OPTIONS", "DELETE")
//                .maxAge(3600)
                .allowCredentials(true);
    }

    /**
     * 视图跳转控制器
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/requestMatch").setViewName("views/requestMatch");
        registry.addViewController("/").setViewName("index");
    }

    /**
     * 配置视图解析器
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setSuffix(".html");
//        registry.viewResolver(viewResolver);
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        System.out.println(converters);
//    }
}
