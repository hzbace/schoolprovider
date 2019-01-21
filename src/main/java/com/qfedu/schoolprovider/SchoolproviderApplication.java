package com.qfedu.schoolprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("com.qfedu.dao")

@ImportResource("classpath:nprovider.xml")
@ServletComponentScan("com.qfedu.nprovider.druid")
public class SchoolproviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolproviderApplication.class, args);
    }

}

