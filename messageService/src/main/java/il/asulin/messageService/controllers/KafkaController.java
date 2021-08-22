package il.asulin.messageService.controllers;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import il.asulin.messageService.services.KafkaService;
import il.asulin.messageService.services.MessageObserver;
import il.asulin.messageService.utils.JsonConverter;

@RestController
@RequestMapping(value = "/messageService")
public class KafkaController {

	private final KafkaService service;
	private final MessageObserver messageObserver;

	@Autowired
	public KafkaController(final KafkaService kafkaService, final MessageObserver messageObserver) {
		this.service = kafkaService;
		this.messageObserver = messageObserver;
	}

	@PostMapping("post")
	public ResponseEntity<?> getJsonObject(@RequestBody String jsonString) {
		try {
			String convertedString = JsonConverter.convertJsonStringToXML(jsonString);//converting the JSON Object ot XML format
			UUID id = UUID.randomUUID();
			this.service.sendToProducer(id, convertedString);//Send to the Producer the XML format
			JSONObject json = messageObserver.waitForMessage(id);//Waiting for response from the Consumer - getting the JSON object
			return new ResponseEntity<String>(json.toString(),HttpStatus.OK);
		} catch (JSONException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (KafkaException | InterruptedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
