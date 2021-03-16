

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpConnectorHelper {
	private  final String USER_AGENT = "Mozilla/5.0";

	private  final String GET_URL = "https://localhost:9090/SpringMVCExample";

	private  final String POST_URL = "http://168.61.22.57:6380/createuser";

	
	public static void main(String[] args) throws IOException, JSONException {

		JSONObject input= new JSONObject();
		JSONObject payload= new JSONObject();
		JSONObject params = new JSONObject();
		params.put("_state", true);
		JSONObject device = new JSONObject();
		device.put("id", "5ed9eaedef89d522950443b9");
		JSONObject customData = new JSONObject();
		customData.put("switch_no","1" );
		customData.put("ud_id", "5e8c448415488a1a68459dd3");
		device.put("customData", customData);
		payload.put("params", params);
		payload.put("command", "action.devices.commands.OnOff");
		payload.put("device", device);
		input.put("payload", payload);
		input.put("intent", "action.entities.EXEC");
		// sendGET();
		System.out.println("GET DONE");
		 new HttpConnectorHelper().sendPostWithToken("https://api.kiot.io/integrations/ctp/go","d1b30b7085bf715f62afbf72aa3b6e987d482c784b05ecaf560e5413ec27", input);
		System.out.println("POST DONE");
	}

	/*private static void sendGET() throws IOException {
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}

	}
*/
	public HashMap<String,String> sendPost(String url, JSONObject params, int paramsFlag) throws IOException {
		HashMap<String,String> map = new HashMap<>();
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
	//	con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		System.out.println(params.toString());
		os.write(params.toString().getBytes());	
		os.flush();
		os.close();
		if(paramsFlag  > 0) {
			
		} else {
			
		}
		
		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			inputLine = response.toString().substring(1, response.toString().length()-1);           //remove curly brackets
			String[] keyValuePairs = inputLine.split(",");              //split the string to creat key-value pairs
			System.out.println("test "+inputLine.toString());

			for(String pair : keyValuePairs)                        //iterate over the pairs
			{
				
				String[] entry = pair.split(":");      
				//split the pairs to get key and value
				String[] keys = entry[0].split("\"");
				String[] values = entry[1].split("\"");
				map.put(keys[1].trim(), values[1].trim());
			    
			    //add them to the hashmap and trim whitespaces
			}
			in.close();
			
			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}
		return map;
	}
	
	public int sendPostWithToken(String url,String token, JSONObject params) throws IOException, JSONException {
	
		JSONObject jsonObject1 = null;
		try {
		HashMap<String,String> map = new HashMap<>();
		JSONArray jsonObject = null;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Bearer "+token);
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		System.out.println(params.toString());
		os.write(params.toString().getBytes());	
		os.flush();
		os.close();
		int responseCode = con.getResponseCode();
		System.out.println(new Timestamp(System.currentTimeMillis())+ " POST Response Code :: " + responseCode +  "    "+params.toString());

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				jsonObject1 = new JSONObject(response.toString());
				
				 in.close();
				con.disconnect();
				System.out.println(new Timestamp(System.currentTimeMillis())+ " Response :" + (int)jsonObject1.get("status") + responseCode +"  "+params.toString());
				 if ((int)jsonObject1.get("status") == 1 && responseCode == 200) {
					 return 1;
				 }  else {
					 return 0;
				 }
			
			} 
			
		else {
				System.out.println("POST request not worked");
				return 0;
			}
			
		
		
	}
		catch(Exception e) {
			return -1;
			
		}
	}
	
	public int sendPostWithToken(String url) throws IOException, JSONException {
		
		JSONObject jsonObject1;
		try {
		HashMap<String,String> map = new HashMap<>();
		
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();	
		os.flush();
		os.close();
		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			if (responseCode != 200) {
				con.disconnect();
				return -1;
			} else {
				con.disconnect();
				return 1;
			}
			
				
				// print result
				
			} 
			
		else {
				System.out.println("POST request not worked");
			}
			
			System.out.println("POST request not worked");
		
		
	}
		catch(Exception e) {
			return -1;
			
		}
		return 1;
	}
}