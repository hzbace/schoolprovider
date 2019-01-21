package com.qfedu.schoolprovider.config;

import com.qfedu.tool.util.JedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JedisConfig {
    @Bean
    public JedisUtil createJedis(){
        return new JedisUtil("39.105.189.141",6379,"qfjava");
    }

}
