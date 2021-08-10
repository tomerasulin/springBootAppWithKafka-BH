package il.asulin.messageService.services;

import java.util.concurrent.CountDownLatch;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import il.asulin.messageService.consumers.Consumer;
import il.asulin.messageService.producers.Producer;
import il.asulin.messageService.timer.CustomTime;

/**
 * This class responsible to wait for the response from the consumer that consume messages from topic2
 * we give a timeout for the response to come and when it pass the timeout we throw an exception
 * In case we get notified in time we proceed to the next step with the return value(JSON Object) 
 * @author tomer
 *
 */
public class MessageObserver implements MessageListener {

	private final CustomTime time;
	private JSONObject json;
	private CountDownLatch latch;
	private static final Logger logger = LoggerFactory.getLogger(Producer.class.getName());

	@Autowired
	private Consumer consumer;

	public MessageObserver(final CustomTime time) {
		this.time = time;
	}
	
	public JSONObject waitForMessage() throws Exception {
		latch = new CountDownLatch(1); // create a countdown size 1
		consumer.registerListener(this); // subscribe a listener
		if (latch.await(time.getTimeout(), time.getTimeUnit())) {//wait for response
			logger.info("Notified");
			return json;
		}
		throw new Exception("Could not get message within " + time);
	}

	@Override
	public void notifyOnMessageReceived(JSONObject json) {
		this.json = json;
		latch.countDown();// decrease the countdown by 1
	}
	
	

}
