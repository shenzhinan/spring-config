package com.aeroinfo.springcloud.consumer;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.aeroinfo.springcloud.producer.ProducerResponse;


@RefreshScope
@EnableCircuitBreaker
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class Consumer {

  @Autowired
  ProducerClient client;
  @Value("${defaultnumber}")
  int number;

  @RequestMapping("/cs/getnumber")
  ProducerResponse consume() {
	  ProducerResponse response = client.getProducerResponse();  
    return response;
  }
  @RequestMapping("/cs/getdefaultnumber")
  ProducerResponse defaultService() {
    return new ProducerResponse(number);
  }
  public static void main(String[] args) {
		// Tell server to look for registration.properties or registration.yml
		//System.setProperty("spring.config.name", "consumer");
		SpringApplication.run(Consumer.class, args);
	}
}

