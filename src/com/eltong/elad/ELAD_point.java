package com.eltong.elad;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import android.widget.TextView;
import android.widget.Toast;

public class ELAD_point extends Activity {
	
	private TextView tv_txt_kanguanggao;
	private TextView tv_txt_canyudiaoyan;
	private TextView tv_txt_goumai;
	private TextView tv_txt_heji;
	int heji = 0;
	
	private Button point_button_return;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_point);
		
		tv_txt_kanguanggao = (TextView)findViewById(R.id.tv_txt_kanguanggao);
		tv_txt_canyudiaoyan = (TextView) findViewById(R.id.tv_txt_canyudiaoyan);
		tv_txt_goumai = (TextView) findViewById(R.id.tv_txt_goumai);
		tv_txt_heji = (TextView) findViewById(R.id.tv_txt_heji);
		
		point_button_return = (Button) findViewById(R.id.point_button_return);
		point_button_return.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ELAD_point.this.finish();
			}
		});	

		JiFenTongJi();
	}
	
	private void JiFenTongJi() {
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_JiFenTongJi,
						strParam.toString());
				doReadJiFenTongJiXML(is);
			}
		}.start();
	}
	
	public void doReadJiFenTongJiXML(InputStream is) {
		if (is == null) {
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		} else {
			try {
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
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG272.equals(nodeName)) {
							String admoney = xpp.nextText();
							heji += Integer.parseInt(admoney);
							Message msg = new Message();
							msg.what = 3;
							msg.obj = admoney;
							handler.sendMessage(msg);
						}
						if (BasicProperties.XML_TAG273.equals(nodeName)) {
							String researchmoney = xpp.nextText();
							heji += Integer.parseInt(researchmoney);
							Message msg = new Message();
							msg.what = 4;
							msg.obj = researchmoney;
							handler.sendMessage(msg);
						}
						if (BasicProperties.XML_TAG274.equals(nodeName)) {
							String buymoney = xpp.nextText();
							heji -= Integer.parseInt(buymoney);
							Message msg = new Message();
							msg.what = 5;
							msg.obj = buymoney;
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
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT)
						.show();
				break;

			case 3:
				String admoneya = (String) msg.obj;
				tv_txt_kanguanggao.setText(admoneya);
				tv_txt_heji.setText(""+heji);
				break;
			case 4:
				String researchmoneya = (String) msg.obj;
				tv_txt_canyudiaoyan.setText(researchmoneya);
				tv_txt_heji.setText(""+heji);
				break;
			case 5:
				String buymoneya = (String) msg.obj;
				tv_txt_goumai.setText(buymoneya);
				tv_txt_heji.setText(""+heji);
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
