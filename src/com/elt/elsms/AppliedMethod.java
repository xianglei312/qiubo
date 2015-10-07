package com.elt.elsms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AppliedMethod {
	
	public static InputStream doGetSubmit(String strURL, String param) {
		InputStream is = null;
		try {
			String urlName = strURL + "?" + param;   
			URL url =  new URL(urlName);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			//connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.addRequestProperty("User-Agent","AppleWebKit/533.18.1 (KHTML, like Gecko) Version/5.0.2 Safari/533.18.5/Qubeon");   
			connection.setRequestMethod(BasicPropertiess.HTTP_REQUEST_METHOD);
			connection.setConnectTimeout(10000);
			//connection.connect(); 
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = connection.getInputStream();
			}
			
			/* for debug */
			
			else {		        
				InputStream err = connection.getErrorStream();
		        StringBuilder sb = new StringBuilder();
		        String line;
		        try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(err, "UTF-8"));
		            while ((line = reader.readLine()) != null) {
		                sb.append(line);
		            }
		        } finally {
		        	err.close();
		        }
		        /* for debug */  
		        //String errMsg = sb.toString();
				//int statusCode = connection.getResponseCode();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}

}
