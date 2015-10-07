package com.eltong.elad;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ELAD_persona extends Activity {
	
	private Button settab_Button = null;
	private ArrayList<HashMap<String, Object>> list = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_persona);
		
//		final EditText quit_password = (EditText)findViewById(R.id.quit_password);
		
		settab_Button = (Button) findViewById(R.id.tabcontent_settab_Button);
//		tabcontent_settab_quit=(Button) llc.findViewById(R.id.tabcontent_settab_quit);
//		tabcontent_settab_quit.setOnClickListener(new View.OnClickListener() {
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			if(!quit_password.getText().toString().trim().equals("")){
//				AlertDialog alertDialog = new AlertDialog.Builder(TabSegmentServiceActivity.this)
//				.setTitle(getString(R.string.ad_val_title))
//				.setMessage(getString(R.string.tabcontent_settab_quit_tishi))
//				.setPositiveButton(getString(R.string.ad_val_ok),
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//								doQuit(quit_password.getText().toString().trim());
//							}
//						})
//				.setNegativeButton(getString(R.string.ad_val_cancle),
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//								return;
//							}
//						}).create();
//		        alertDialog.show();
//			}else
//			{
//				Toast.makeText(TabSegmentServiceActivity.this,
//						getString(R.string.toast_msg_pass),
//						Toast.LENGTH_SHORT).show();
//			}
//		}
//	});	
		CheckModifyDay();
	}
	
	private void CheckModifyDay() {
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_CheckModifyDay,
						strParam.toString());
				doReadCheckModifyDayXML(is);
				
				settab_Button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (list.size() > 0) {
							final HashMap<String, Object> map = (HashMap<String, Object>) list
									.get(0);
							Intent intent = new Intent();
							intent.putExtra(BasicProperties.XML_TAG162, map
									.get(BasicProperties.XML_TAG162).toString());
							intent.putExtra(BasicProperties.XML_TAG163, map
									.get(BasicProperties.XML_TAG163).toString());
							intent.putExtra(BasicProperties.XML_TAG164, map
									.get(BasicProperties.XML_TAG164).toString());
							intent.putExtra(BasicProperties.XML_TAG165, map
									.get(BasicProperties.XML_TAG165).toString());
							intent.putExtra(BasicProperties.XML_TAG166, map
									.get(BasicProperties.XML_TAG166).toString());
							intent.putExtra(BasicProperties.XML_TAG175, map
									.get(BasicProperties.XML_TAG175).toString());
							intent.putExtra(BasicProperties.XML_TAG178, map
									.get(BasicProperties.XML_TAG178).toString());
							intent.setClass(ELAD_persona.this, SignupUpdate.class);
							startActivity(intent);
							ELAD_persona.this.finish();
						} else {
							Toast.makeText(
									ELAD_persona.this,
									getString(R.string.tabcontent_settab_Button_tishi),
									Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		}.start();
	}
	
	public void doReadCheckModifyDayXML(InputStream is) {
		Message msg = new Message();
		if (is == null) {
			msg.what = 0;
			msg.obj = getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				HashMap<String, Object> map = null;
				String testString = convertStreamToString(is);
				String resultString = testString.replaceAll("\n", "");
				InputStream iiss = new ByteArrayInputStream(
						resultString.getBytes());
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(iiss, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						list = new ArrayList<HashMap<String, Object>>();
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							map = new HashMap<String, Object>();
						} else if (BasicProperties.XML_TAG161.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG161, xpp.nextText());
						} else if (BasicProperties.XML_TAG162.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG162, xpp.nextText());
						} else if (BasicProperties.XML_TAG163.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG163, xpp.nextText());
						} else if (BasicProperties.XML_TAG164.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG164, xpp.nextText());
						} else if (BasicProperties.XML_TAG165.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG165, xpp.nextText());
						} else if (BasicProperties.XML_TAG166.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG166, xpp.nextText());
						} else if (BasicProperties.XML_TAG175.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG175, xpp.nextText());
						} else if (BasicProperties.XML_TAG178.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG178, xpp.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							list.add(map);
						}
						break;
					}
					eventCode = xpp.next();
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};
	
	public String convertStreamToString(InputStream is) throws IOException {
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
