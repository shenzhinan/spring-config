package com.aeroinfo.springcloud.consumer2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rx.Observable;
import rx.Subscriber;

import com.aeroinfo.springcloud.producer2.ProducerResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Service
public class ProducerClient2 {
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getProducerFallBack")
	public ProducerResponse getProducerResponse(){
		return restTemplate.getForObject("http://producer-service2/producestring", ProducerResponse.class);
	}
	public ProducerResponse getProducerFallBack(){
		return new ProducerResponse("aaa");
	}
}

