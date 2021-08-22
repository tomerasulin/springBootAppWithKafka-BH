package il.asulin.messageService.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import il.asulin.messageService.configurations.Config;
import il.asulin.messageService.consumers.Consumer;
import il.asulin.messageService.producers.Producer;
import il.asulin.messageService.timer.CustomTime;
import il.asulin.messageService.timer.TimeResolver;

/**
 * This class responsible to wait for the response from the consumer that consume messages from topic2
 * we give a timeout for the response to come and when it pass the timeout we throw an exception
 * In case we get notified in time we proceed to the next step with the return value(JSON Object) 
 * @author tomer
 *
 */
@Component
public class MessageObserver implements MessageListener {

	private final CustomTime time;
	private Map<UUID, CountDownLatch> timeouts = new HashMap<UUID, CountDownLatch>();
	private Map<UUID,JSONObject> responses = new HashMap<UUID, JSONObject>();
	private static final Logger logger = LoggerFactory.getLogger(Producer.class.getName());
	
	@Autowired
	public MessageObserver(final Consumer consumer, final Config config) {
		TimeResolver timeResolver = new TimeResolver() {};
		this.time = timeResolver.resolveTime("env.timeout", config.getTimeout());
		consumer.registerListener(this); // subscribe a listener
	}
	
	public JSONObject waitForMessage(final UUID id) throws Exception {
		final CountDownLatch latch = new CountDownLatch(1);
		timeouts.put(id, latch);
		if (latch.await(time.getTimeout(), time.getTimeUnit())) {//wait for response			
			logger.info("Notified");
			JSONObject resJson = responses.get(id);
			responses.remove(id);
			return resJson;
		}
		throw new Exception("Could not get message within " + time);
	}

	@Override
	public void notifyOnMessageReceived(JSONObject json, String key) {
		final UUID fromKey = UUID.fromString(key);
		responses.put(fromKey, json);
		timeouts.get(fromKey).countDown();// decrease the countdown by 1
		timeouts.remove(fromKey);
	}
	
	

}
