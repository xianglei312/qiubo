package com.eltong.elad;

import java.io.IOException;
import java.io.InputStream;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ELAD_coupon extends Activity {
	
	private ListView tabcontent_service_coupon_true = null;
	private SimpleAdapter saCouponT = null;
	private ArrayList<HashMap<String, Object>> listCouponT = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_coupon);
		tabcontent_service_coupon_true = (ListView) findViewById(R.id.tabcontent_service_coupon_true);
		tabcontent_service_coupon_true.setOnItemClickListener(iclaaa);
		getcouponList();
	}
	
	private OnItemClickListener iclaaa = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			final HashMap<String, Object> map = (HashMap<String, Object>) tabcontent_service_coupon_true
					.getItemAtPosition(arg2);
			Intent intent = new Intent();
			intent.setClass(ELAD_coupon.this,
					CouponDetailed.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("textviewa", map.get(
					BasicProperties.XML_TAG34).toString());
			bundle.putString("textviewb", map.get(
					BasicProperties.XML_TAG38).toString());
			bundle.putString("textviewc", map.get(
					BasicProperties.XML_TAG37).toString());
			
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
	
	private void getcouponList() {
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetCouponHistory,
						strParam.toString());
				doReadCouponListXML(is);
				Message msg = new Message();
				msg.what = 11;
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	public void doReadCouponListXML(InputStream is) {
		Message msg = new Message();
		String valid = "0";
		if (is == null) {
			msg.what = 0;
			msg.obj = getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				HashMap<String, Object> map = null;
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(is, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						listCouponT = new ArrayList<HashMap<String, Object>>();
						//listCouponF = new ArrayList<HashMap<String, Object>>();
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							map = new HashMap<String, Object>();
						} else if (BasicProperties.XML_TAG33.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG33, xpp.nextText());
						} else if (BasicProperties.XML_TAG34.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG34, xpp.nextText());
						} else if (BasicProperties.XML_TAG35.equals(nodeName)) {
							String period = xpp.nextText().substring(0, 10);
							map.put(BasicProperties.XML_TAG35, period);
						} else if (BasicProperties.XML_TAG36.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG36, xpp.nextText());
							valid =map.get(BasicProperties.XML_TAG36).toString();
						} else if (BasicProperties.XML_TAG37.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG37, xpp.nextText());
						} else if (BasicProperties.XML_TAG38.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG38, xpp.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							if (valid.equals("0")) {
								//listCouponF.add(map);
							} else {
								listCouponT.add(map);
							}
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
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;
			case 11:
				saCouponT = new SimpleAdapter(ELAD_coupon.this,
						listCouponT, R.layout.elad_coupon_list, new String[] {
								BasicProperties.XML_TAG34,
								BasicProperties.XML_TAG35 }, new int[] {
								R.id.tabcontent_service_coupon_title,
								R.id.tabcontent_service_coupon_time });
				tabcontent_service_coupon_true.setAdapter(saCouponT);
				break;

			}
		};
	};
}
