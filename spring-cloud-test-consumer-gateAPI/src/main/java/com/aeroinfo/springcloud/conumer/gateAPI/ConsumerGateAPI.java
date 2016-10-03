package com.aeroinfo.springcloud.conumer.gateAPI;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

import com.aeroinfo.springcloud.consumer.ConsumerService;
import com.aeroinfo.springcloud.consumer2.ConsumerService2;


@EnableCircuitBreaker
@RestController
@EnableDiscoveryClient
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses  = {com.aeroinfo.springcloud.consumer.ConsumerService.class, com.aeroinfo.springcloud.consumer2.ConsumerService2.class})
public class ConsumerGateAPI {

  @Autowired
  ConsumerService client1;
  @Autowired
  ConsumerService2 client2;


  @RequestMapping("/cg/getresponse")
  DeferredResult<ResponseDetails> getResposne() {
	  DeferredResult<ResponseDetails> result = new DeferredResult<ResponseDetails>();
	  Observable.zip(
			  client1.getProducerResponse(),
			  client2.getProducerResponse(),
			  (response1, response2)->{
				  ResponseDetails responseDetails = new ResponseDetails();
				  responseDetails.setNumber(response1.getValue());
				  responseDetails.setS(response2.getValue());
				  return responseDetails;
			  }
			  ).subscribe(m -> result.setResult(m), e -> result.setErrorResult(e));;	  
	  return result;
  }
  @RequestMapping("/cg/getdefaultresponse")
  DeferredResult<ResponseDetails> getDefaultResposne() {
	  DeferredResult<ResponseDetails> result = new DeferredResult<ResponseDetails>();
	  Observable.zip(
			  client1.getProducerDefaultResponse(),
			  client2.getProducerDefaultResponse(),
			  new Func2<com.aeroinfo.springcloud.producer.ProducerResponse,com.aeroinfo.springcloud.producer2.ProducerResponse,ResponseDetails>(){
				@Override
				public ResponseDetails call(com.aeroinfo.springcloud.producer.ProducerResponse t1,
						com.aeroinfo.springcloud.producer2.ProducerResponse t2) {
					 ResponseDetails responseDetails = new ResponseDetails();
					 responseDetails.setNumber(t1.getValue());
					 responseDetails.setS(t2.getValue());
					 return responseDetails;
				}				  
			  }
			  /*(response1, response2)->{
				  ResponseDetails responseDetails = new ResponseDetails();
				  responseDetails.setNumber(response1.getValue());
				  responseDetails.setS(response2.getValue());
				  return responseDetails;
			  }*/
			  ).subscribe(m -> result.setResult(m), new Action1<Throwable>(){
				@Override
				public void call(Throwable t) {
					result.setErrorResult(t);					
				}				  
			  });;	  
	  return result;
  }
  public static void main(String[] args) {
		// Tell server to look for registration.properties or registration.yml
		//System.setProperty("spring.config.name", "consumer");
		SpringApplication.run(ConsumerGateAPI.class, args);
	}
}
class ResponseDetails{
	int number;
	String s;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	
}

