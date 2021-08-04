package com.spring.company.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	/*@KafkaListener(topics="stockapp",groupId="group_id")
	public void consume(String message){
		System.out.println("Consumed Message:"+message);
	}
	*/
	
	
}
