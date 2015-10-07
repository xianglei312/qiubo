package com.eltong.elad;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetTool {
	public static byte[] getImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(6 * 1000);
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			return readInstream(inputStream);
		}else {
			return null;		
		}
	}
	public static byte[] readInstream(InputStream inputStream) throws Exception{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; 
		int length = -1;
		while ((length = inputStream.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer,0,length);
		}
		byteArrayOutputStream.close();
		inputStream.close();
		return byteArrayOutputStream.toByteArray();
	}
}
