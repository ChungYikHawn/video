package com.hang.hangvideosdev;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.hang.hangvideosdev.mapper")
@EnableSwagger2
public class HangVideosDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(HangVideosDevApplication.class, args);
    }

}
