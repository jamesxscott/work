import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.StringEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;


public class DarwinHPS {

	public static void main(String args[])
	throws ClientProtocolException, IOException, AuthenticationException {
		hpspost();
	}
	
	public static void hpspost() throws ClientProtocolException, IOException, AuthenticationException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("https://hsp-prod.rockshore.net/api/v1/serviceMetrics");
		
		httpPost.setHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json");

		UsernamePasswordCredentials creds = new UsernamePasswordCredentials("jamesxscott@gmail.com", "E0oFceWs@J$8&pP^");
		httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
		
		Map<String, String> comment = new HashMap<String, String>();
		comment.put("from_loc","WOK");
		comment.put("to_loc","WAT");
		comment.put("from_time","0800");
		comment.put("to_time","1000");
		comment.put("from_date","2016-12-31");
		comment.put("to_date","2017-12-31");
		comment.put("days","WEEKDAY");
		//comment.put("rid", "201607294212242");
		String json = new GsonBuilder().create().toJson(comment, Map.class);
		httpPost.setEntity(new StringEntity(json));

		HttpResponse response2 = httpclient.execute(httpPost);

		try {
			System.out.println(response2.getStatusLine());
			// HttpEntity entity2 = response2.getEntity();
			// BufferedReader rd = new BufferedReader(new InputStreamReader(response2.getEntity().getContent()));
            // String line = "";
            // while ((line = rd.readLine()) != null) {
                // System.out.println(line);
            // }
			//parse the JSON object
			// HttpEntity entity = response2.getEntity().getContent();
			// Object content = EntityUtils.toString(entity);
			
			HttpEntity entity = response2.getEntity();
			//String json_string = EntityUtils.toString(entity);
			//System.out.println(json_string);
			
			//JsonObject jobj = new Gson().fromJson(json_string, JsonObject.class);

			//JsonArray servicesArray = jobj.get("Services").getAsJsonArray();
			
			
			//JsonObject servicesObj = jobj.get("Services").getAsJsonObject();
			//JsonArray serviceMetricsArr = servicesObj.get("serviceAttributesMetrics").getAsJsonArray();
			
			//System.out.println(serviceMetricsObj.get("origin_location").getAsString());
			//System.out.println(value);	

			//Write to JSON file

			BufferedInputStream bis = new BufferedInputStream(entity.getContent());
			String filePath = "Darwin.JSON";
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			int inByte;
			while((inByte = bis.read()) != -1) bos.write(inByte);
			bis.close();
			bos.close();

			EntityUtils.consume(entity);
		} finally {
			httpPost.releaseConnection();
			}

	} 	
	}
