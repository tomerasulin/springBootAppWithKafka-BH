package il.asulin.messageService.services;

import org.json.JSONObject;

public interface MessageListener {

	void notifyOnMessageReceived(JSONObject json, String key);
}
