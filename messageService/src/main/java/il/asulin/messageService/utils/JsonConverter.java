package il.asulin.messageService.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * This class capable of converting JSON to XML vice versa
 * @author tomer
 *
 */
public final class JsonConverter {

	private JsonConverter() {}
	
	public static String convertJsonStringToXML(String str) throws JSONException {
		JSONObject json = getJsonFromString(str); 
		return XML.toString(json);
	}
	
	public static JSONObject getJsonFromString(String str) throws JSONException {
		return new JSONObject(str);
	}
	
	public static JSONObject convertXMLToJson(String xml) throws JSONException {
		return XML.toJSONObject(xml);
	}
}
