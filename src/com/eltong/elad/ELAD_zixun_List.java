package com.eltong.elad;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.RTalk.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ELAD_zixun_List extends Activity {
	
	private EditText edittexta;
	private EditText edittextb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_zixun_list);

		edittexta =(EditText)findViewById(R.id.tabcontent_service_zixun_editviewa);
		edittextb =(EditText)findViewById(R.id.tabcontent_service_zixun_editviewb);
		Button tabcontent_service_zixun_Button =(Button)findViewById(R.id.tabcontent_service_zixun_Button);
		tabcontent_service_zixun_Button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dozixunsubmit();
			}
		});
	}
	
	private void dozixunsubmit() {
		new Thread() {
			public void run() {

				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				strParam.append("&zixunneirong=");
				strParam.append(urlUtf(edittexta.getText().toString()));
				strParam.append("&lianxifangshi=");
				strParam.append(urlUtf(edittextb.getText().toString()));
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_Insertzixun,
						strParam.toString());
				doReadzixunXML(is);
			}
		}.start();
	}
	
	public void doReadzixunXML(InputStream is) {
		Message msg = new Message();
		if (is == null) {
			msg.what = 0;
			msg.obj = getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(is, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG265.equals(nodeName)) {
							msg.what = 1;
							msg.obj = xpp.nextText();
							handler.sendMessage(msg);
						}
						break;
					case XmlPullParser.END_TAG:
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
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				String result = msg.obj.toString();
				if(result.equals("1"))
				{
					Toast.makeText(getApplicationContext(), R.string.tabcontent_service_zixun_ok,
						Toast.LENGTH_SHORT).show();
					}
				else
				{
					Toast.makeText(getApplicationContext(), R.string.tabcontent_service_zixun_error,
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		};
	};
	
	private String urlUtf(String text) {
		String urlUtf = "";
		try {
			urlUtf = URLEncoder.encode(text, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlUtf;
	}
}
