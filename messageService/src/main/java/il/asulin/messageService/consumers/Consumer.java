package il.asulin.messageService.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import il.asulin.messageService.producers.Producer;
import il.asulin.messageService.services.MessageListener;
import il.asulin.messageService.utils.JsonConverter;

@Service
public class Consumer {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class.getName());
	private MessageListener listener;

	// subscribe a listener for the Consumer class that will be notify when the
	// message received from Topic2
	public void registerListener(MessageListener listener) {
		this.listener = listener;
	}

	@KafkaListener(topics = Producer.TOPIC_1, groupId = "my-springboot-app")
	@SendTo(Producer.TOPIC_2)
	public String consumeFromTopic1(ConsumerRecord<String, String> record) {
		logger.info("Consume from TOPIC_1: " + record.value() + "\n\n");
		return record.key() + record.value();
	}

	@KafkaListener(topics = Producer.TOPIC_2, groupId = "my-springboot-app")
	public void consumeFromTopic2(String record) {
		String key = record.substring(0, 36);
		String xml = record.substring(36);
		logger.info("Consume from TOPIC_2: " + xml + "\n\n");
		try {
			JSONObject json = JsonConverter.convertXMLToJson(xml);
			if(listener != null)
				listener.notifyOnMessageReceived(json, key);//notify the listener
		}catch(JSONException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
