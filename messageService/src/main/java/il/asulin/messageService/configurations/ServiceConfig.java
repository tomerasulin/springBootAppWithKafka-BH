package il.asulin.messageService.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import il.asulin.messageService.services.MessageObserver;
import il.asulin.messageService.timer.TimeResolver;

/**
 * This class is a configuration class that creates bean that by managed by the spring container
 * @author tomer
 *
 */
@Configuration
public class ServiceConfig {

	/**
	 * creating the CustomTime for the MessageObserver class
	 * @param timeResolver
	 * @param timeout - getting from the enviorment variable in the application.properties file
	 * @return
	 */
	@Bean
	public MessageObserver getMessageObserver(TimeResolver timeResolver, @Value("${env.timeout}") String timeout) {
		return new MessageObserver(timeResolver.resolveTime("env.timeout", timeout));
	}

	@Bean
	public TimeResolver getTimeResolver() {
		return new TimeResolver() {};
	}
}
