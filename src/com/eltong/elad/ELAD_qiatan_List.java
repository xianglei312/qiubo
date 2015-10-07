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

public class ELAD_qiatan_List extends Activity {
	
	private ListView tabcontent_service_Booking = null;
	private ListView tabcontent_service_Consult = null;
	private ArrayList<HashMap<String, Object>> listBooking = null;
	private SimpleAdapter saBooking = null;
	private ArrayList<HashMap<String, Object>> listConsult = null;
	private SimpleAdapter saConsult = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_qiatan_list);

		tabcontent_service_Booking = (ListView)findViewById(R.id.tabcontent_service_booking);
		tabcontent_service_Booking.setOnItemClickListener(iclb);
		tabcontent_service_Consult = (ListView)findViewById(R.id.tabcontent_service_consult);
		tabcontent_service_Consult.setOnItemClickListener(iclc);
		getbookingList();
		getconsultList();
		
	}
	
	private void getbookingList() {
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetBookingHistory,
						strParam.toString());
				doReadBookingListXML(is);
				Message msg = new Message();
				msg.what = 13;
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	public void doReadBookingListXML(InputStream is) {
		Message msg = new Message();
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
						listBooking = new ArrayList<HashMap<String, Object>>();
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							map = new HashMap<String, Object>();
						} else if (BasicProperties.XML_TAG137.equals(nodeName)) {
							String Booking_daya = xpp.nextText();
							String Booking_day = Booking_daya
									.substring(0, 10)+" "+Booking_daya.substring(11, 19);
							map.put(BasicProperties.XML_TAG137, Booking_day);
						} else if (BasicProperties.XML_TAG138.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG138, xpp.nextText());
						} else if (BasicProperties.XML_TAG139.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG139, xpp.nextText());
						} else if (BasicProperties.XML_TAG140.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG140, xpp.nextText());
						} else if (BasicProperties.XML_TAG141.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG141, xpp.nextText());
						} else if (BasicProperties.XML_TAG142.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG142, xpp.nextText());
						} else if (BasicProperties.XML_TAG143.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG143, xpp.nextText());
						} else if (BasicProperties.XML_TAG144.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG144, xpp.nextText());
						} else if (BasicProperties.XML_TAG145.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG145, xpp.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							listBooking.add(map);
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
	
	private OnItemClickListener iclb = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			final HashMap<String, Object> map = (HashMap<String, Object>) tabcontent_service_Booking
					.getItemAtPosition(arg2);
			Intent intent = new Intent();
			intent.setClass(ELAD_qiatan_List.this,
					BookingDetailed.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("textviewa", map.get(
					BasicProperties.XML_TAG143).toString());
			bundle.putString("textviewb", map.get(
					BasicProperties.XML_TAG144).toString());
			bundle.putString("textviewc", map.get(
					BasicProperties.XML_TAG145).toString());
			bundle.putString("textviewd", map.get(
					BasicProperties.XML_TAG137).toString());
			bundle.putString("textviewe", map.get(
					BasicProperties.XML_TAG141).toString());
			bundle.putString("textviewf", map.get(
					BasicProperties.XML_TAG142).toString());
			bundle.putString("textviewg", map.get(
					BasicProperties.XML_TAG140).toString());
			
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
	
	private void getconsultList() {
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetConsultHistory,
						strParam.toString());
				doReadConsultListXML(is);
				Message msg = new Message();
				msg.what = 14;
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	public void doReadConsultListXML(InputStream is) {
		Message msg = new Message();
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
						listConsult = new ArrayList<HashMap<String, Object>>();
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							map = new HashMap<String, Object>();
						} else if (BasicProperties.XML_TAG146.equals(nodeName)) {
							String Consult_daya = xpp.nextText();
							String Consult_day = Consult_daya
									.substring(0, 10)+" "+Consult_daya.substring(11, 19);
							map.put(BasicProperties.XML_TAG146, Consult_day);
						} else if (BasicProperties.XML_TAG147.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG147, xpp.nextText());
						} else if (BasicProperties.XML_TAG148.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG148, xpp.nextText());
						} else if (BasicProperties.XML_TAG149.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG149, xpp.nextText());
						} else if (BasicProperties.XML_TAG150.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG150, xpp.nextText());
						} else if (BasicProperties.XML_TAG151.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG151, xpp.nextText());
						} else if (BasicProperties.XML_TAG152.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG152, xpp.nextText());
						} else if (BasicProperties.XML_TAG153.equals(nodeName)) {
							map.put(BasicProperties.XML_TAG153, xpp.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							listConsult.add(map);
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
	
	private OnItemClickListener iclc = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			final HashMap<String, Object> map = (HashMap<String, Object>) tabcontent_service_Consult
					.getItemAtPosition(arg2);
			Intent intent = new Intent();
			intent.setClass(ELAD_qiatan_List.this,
					ConsultDatailed.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("textviewa", map.get(
					BasicProperties.XML_TAG151).toString());
			bundle.putString("textviewb", map.get(
					BasicProperties.XML_TAG152).toString());
			bundle.putString("textviewc", map.get(
					BasicProperties.XML_TAG153).toString());
			bundle.putString("textviewd", map.get(
					BasicProperties.XML_TAG146).toString());
			bundle.putString("textviewe", map.get(
					BasicProperties.XML_TAG149).toString());
			bundle.putString("textviewf", map.get(
					BasicProperties.XML_TAG150).toString());
			bundle.putString("textviewg", map.get(
					BasicProperties.XML_TAG148).toString());
			
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;
			case 13:
				saBooking = new SimpleAdapter(ELAD_qiatan_List.this,
						listBooking, R.layout.elad_servicetab_booking, new String[] {
								BasicProperties.XML_TAG138,
								BasicProperties.XML_TAG137 }, new int[] {
								R.id.tabcontent_service_booking_title,
								R.id.tabcontent_service_booking_time });
				tabcontent_service_Booking.setAdapter(saBooking);
				break;
			case 14:
				saConsult = new SimpleAdapter(ELAD_qiatan_List.this,
						listConsult, R.layout.elad_servicetab_consult, new String[] {
								BasicProperties.XML_TAG147,
								BasicProperties.XML_TAG146 }, new int[] {
								R.id.tabcontent_service_consult_title,
								R.id.tabcontent_service_consult_time });
				tabcontent_service_Consult.setAdapter(saConsult);

				break;
			}
		};
	};
}
