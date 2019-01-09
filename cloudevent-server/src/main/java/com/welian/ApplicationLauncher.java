package com.welian;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Description: 应用启动类
 * Date: 2016/10/14 15:40
 *
 * @author Sean.xie
 */
@EnableScheduling
@SpringCloudApplication
@EnableFeignClients("com.welian.client")
public class ApplicationLauncher {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationLauncher.class, args);
    }

    @Bean
    public Logger.Level felev(){
        return Logger.Level.FULL;
    }
}

