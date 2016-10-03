package com.aeroinfo.springcloud.configserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableConfigServer
//@EnableDiscoveryClient
public class ConfigServerApplication {
 
    public static void main(String[] args) {
    	//System.setProperty("spring.config.name", "config-server");
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}

