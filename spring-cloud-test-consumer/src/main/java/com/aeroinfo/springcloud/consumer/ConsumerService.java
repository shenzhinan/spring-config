package com.aeroinfo.springcloud.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rx.Observable;
import rx.Subscriber;

import com.aeroinfo.springcloud.producer.ProducerResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ConsumerService {
	@Autowired
	RestTemplate restTemplate;
	@Value("${defaultnumber}")
	int number;
	@HystrixCommand(fallbackMethod = "getProducerFallBack")
	public Observable<ProducerResponse> getProducerResponse() {
		return Observable
				.create(new Observable.OnSubscribe<ProducerResponse>() {
					public void call(Subscriber<? super ProducerResponse> observer) {
						try {
							if (!observer.isUnsubscribed()) {
								observer.onNext(restTemplate.getForObject("http://producer-service/producenumber", ProducerResponse.class));
								observer.onCompleted();
							}
						} catch (Exception e) {
							observer.onError(e);
						}
					}
				});
		/*
		 * return new ObservableResult<ProducerResponse>(){ return
		 * restTemplate.getForObject("http://producer-service/producenumber",
		 * ProducerResponse.class); }
		 */

	}

	@HystrixCommand(fallbackMethod = "getProducerFallBack")
	public Observable<ProducerResponse> getProducerDefaultResponse() {
		return Observable
				.create(new Observable.OnSubscribe<ProducerResponse>() {
					public void call(Subscriber<? super ProducerResponse> observer) {
						try {
							if (!observer.isUnsubscribed()) {
								observer.onNext(new ProducerResponse(number));
								observer.onCompleted();
							}
						} catch (Exception e) {
							observer.onError(e);
						}
					}
				});
		/*
		 * return new ObservableResult<ProducerResponse>(){ return
		 * restTemplate.getForObject("http://producer-service/producenumber",
		 * ProducerResponse.class); }
		 */

	}
	public ProducerResponse getProducerFallBack() {
		return new ProducerResponse(42);
	}
}
