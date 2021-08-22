package il.asulin.messageService.timer;

import java.util.concurrent.TimeUnit;

/**
 * Class that handle time <long, TimeUnit> that pass to the await() function of the countDownLatch
 * @author tomer
 *
 */
public class CustomTime {

	private final long timeout;
	private final TimeUnit timeUnit;

	public CustomTime(long timeout, TimeUnit timeUnit) {
		this.timeout = timeout;
		this.timeUnit = timeUnit;
	}

	public long getTimeout() {
		return timeout;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	@Override
	public String toString() {
		return "CustomTime [timeout=" + timeout + ", timeUnit=" + timeUnit + "]";
	}

	
}
