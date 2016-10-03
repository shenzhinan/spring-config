package com.aeroinfo.springcloud.consumer2;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rx.Observable;

import com.aeroinfo.springcloud.producer2.ProducerResponse;


@RefreshScope
@EnableCircuitBreaker
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class Consumer2 {

  @Autowired
  ProducerClient2 client;
  @Value("${defaultstring}")
  String s;

  @RequestMapping("/cs2/getstring")
  ProducerResponse consume() {
	  ProducerResponse response = client.getProducerResponse();  
    return response;
  }
  @RequestMapping("/cs2/getdefaultstring")
  ProducerResponse defaultService() {
    return new ProducerResponse(s);
  }
  public static void main(String[] args) {
		// Tell server to look for registration.properties or registration.yml
		//System.setProperty("spring.config.name", "consumer");
		SpringApplication.run(Consumer2.class, args);
	}
}

