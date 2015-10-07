package com.elt.elsms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConvertStreamToString {
	/**
	 * @author XXD
	 * @note 将InputStream转换成String方法
	 */
	public static String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} finally {
				is.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}
}
