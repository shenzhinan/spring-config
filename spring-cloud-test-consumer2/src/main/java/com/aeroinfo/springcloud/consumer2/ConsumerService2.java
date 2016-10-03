package com.aeroinfo.springcloud.consumer2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rx.Observable;
import rx.Subscriber;

import com.aeroinfo.springcloud.producer2.ProducerResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Service
public class ConsumerService2 {
	@Autowired
	RestTemplate restTemplate;
	@Value("${defaultstring}")
	String s;
	@HystrixCommand(fallbackMethod = "getProducerFallBack")
	public Observable<ProducerResponse> getProducerResponse(){
		return Observable
				.create((observer)-> {
						try {
							if (!observer.isUnsubscribed()) {
								observer.onNext(restTemplate.getForObject("http://producer-service2/producestring", ProducerResponse.class));
								observer.onCompleted();
							}
						} catch (Exception e) {
							observer.onError(e);
						}
					}
				);
	}
	@HystrixCommand(fallbackMethod = "getProducerFallBack")
	public Observable<ProducerResponse> getProducerDefaultResponse(){
		return Observable
				.create(new Observable.OnSubscribe<ProducerResponse>() {
					public void call(Subscriber<? super ProducerResponse> observer) {
						try {
							if (!observer.isUnsubscribed()) {
								observer.onNext(new ProducerResponse(s));
								observer.onCompleted();
							}
						} catch (Exception e) {
							observer.onError(e);
						}
					}
				});
	}
	public ProducerResponse getProducerFallBack(){
		return new ProducerResponse("aaa");
	}
}

