package com.aeroinfo.springcloud.producer2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class Producer {

  String counter = "s";

  @RequestMapping("/producestring")
  ProducerResponse produce() {
	  ProducerResponse response = new ProducerResponse();
	  counter=counter+"s";
	  response.setValue(counter);
    return response;
  }
  public static void main(String[] args) {
		// Tell server to look for registration.properties or registration.yml
		//System.setProperty("spring.config.name", "producer");

		SpringApplication.run(Producer.class, args);
  }

}