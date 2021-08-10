package il.asulin.messageService.producers;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class.getName());
	public static final String TOPIC_1 = "topic_1";
	public static final String TOPIC_2 = "topic_2";
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public Producer(final KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessageToTopic(String msg) {
		logger.info("Produce to XML_TOPIC_1: " + msg + "\n\n");
		this.kafkaTemplate.send(TOPIC_1, msg);
	}
}
