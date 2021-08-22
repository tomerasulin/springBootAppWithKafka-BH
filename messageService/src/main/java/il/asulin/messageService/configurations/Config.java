package il.asulin.messageService.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;


@ConfigurationProperties("env")
@Component
@Data
@NoArgsConstructor
public class Config {

	private String timeout;

}
