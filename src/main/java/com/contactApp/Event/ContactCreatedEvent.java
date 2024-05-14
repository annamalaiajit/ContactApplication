package com.contactApp.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.contactApp.AppConstants;
import com.contactApp.model.ContactModel;

@Component
public class ContactCreatedEvent {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void send(ContactModel message) {

		kafkaTemplate.send(AppConstants.TOPIC_NAME, message);
	}

}
