package com.hang.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class WebConfig {
//
//    /**
//     * 配置静态资源处理方式一：使用容器的默认Servlet处理
//     */
//    /*@Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        //启用默认Servlet支持
//        configurer.enable();
//    }*/
//
//    /**
//     * 配置静态资源处理方式二：springmvc处理静态资源
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("/static/");
//    }
//
//    /**
//     * 添加拦截器
//     * addInterceptor方法指定自定义拦截器对象
//     * addPathPatterns方法指定哪些请求经过拦截器
//     * excludePathPatterns方法指定哪些请求放行
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//    }
//
//    /**
//     * 配置commons-upload上传
//     */
//    @Bean
//    public CommonsMultipartResolver multipartResolver(){
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        //设置限制上传大小
//        resolver.setMaxUploadSize(104857600);
//        //设置编码
//        resolver.setDefaultEncoding("UTF-8");
//        return resolver;
//    }
//
//    /**
//     * 配置默认的视图解析器
//     */
//    @Bean
//    public InternalResourceViewResolver viewResolver(){
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/pages/");
//        viewResolver.setSuffix(".jsp");
//        //如果页面需要使用JSTL标签库
//        //viewResolver.setViewClass(JstlView.class);
//        return viewResolver;
//    }
}
