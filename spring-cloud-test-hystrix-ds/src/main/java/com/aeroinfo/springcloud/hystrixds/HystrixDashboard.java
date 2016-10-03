package com.aeroinfo.springcloud.hystrixds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableAutoConfiguration
@EnableHystrixDashboard
public class HystrixDashboard {
	public static void main(String[] args) {
		// Tell server to look for registration.properties or registration.yml
		//System.setProperty("spring.config.name", "hystrixds");

		SpringApplication.run(HystrixDashboard.class, args);
	}
}
