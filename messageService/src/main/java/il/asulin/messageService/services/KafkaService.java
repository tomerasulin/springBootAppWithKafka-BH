package il.asulin.messageService.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import il.asulin.messageService.producers.Producer;

@Service
public class KafkaService {

	private final Producer producer;

	@Autowired
	public KafkaService(final Producer producer) {
		this.producer = producer;
	}

	// send the XML format to the first topic
	public void sendToProducer(UUID id, String xml) {
		this.producer.sendMessageToTopic(id, xml);
	}
}
