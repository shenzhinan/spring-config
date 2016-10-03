package com.aeroinfo.springcloud.producer2;

public class ProducerResponse {
	String value;
	public ProducerResponse(){}
	public ProducerResponse(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value=value;
	}
}