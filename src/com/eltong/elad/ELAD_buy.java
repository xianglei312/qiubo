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

public class ELAD_buy extends Activity {
	
	private ListView tabcontent_point_lin_note = null;
	private int goodstype = 0;
	private SimpleAdapter sa = null;
	private ArrayList<HashMap<String, Object>> list = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_buy);
		tabcontent_point_lin_note = (ListView) findViewById(R.id.tabcontent_point_lin_notea);
		tabcontent_point_lin_note.setOnItemClickListener(icl);
		getpointnote();
	}
	
	private void getpointnote() {
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_BuyHistory,
						strParam.toString());
				doReadnoteXML(is);
				sa = new SimpleAdapter(ELAD_buy.this, list,
						R.layout.elad_pointtab_note, new String[] {
								BasicProperties.XML_TAG112,
								BasicProperties.XML_TAG111,
								BasicProperties.XML_TAG119,
								BasicProperties.XML_TAG110,
								BasicProperties.XML_TAG113 }, new int[] {
								R.id.list_note_title, R.id.list_note_point,
								R.id.list_note_sum, R.id.list_note_type,
								R.id.list_note_time });

				Message msg = new Message();
				msg.what = 8;
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	public void doReadnoteXML(InputStream is) {
		if (is == null) {
			Message msg = new Message();
			msg.what = 0;
			msg.obj = getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				HashMap<String, Object> mapnote = null;
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(is, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						list = new ArrayList<HashMap<String, Object>>();
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							mapnote = new HashMap<String, Object>();
						} else if (BasicProperties.XML_TAG110.equals(nodeName)) {
							goodstype = Integer.parseInt(xpp.nextText());
							switch (goodstype) {
							case 0:
								mapnote.put(
										BasicProperties.XML_TAG110,
										getString(R.string.pointtab_note_goodstypea));
								break;
							case 1:
								mapnote.put(
										BasicProperties.XML_TAG110,
										getString(R.string.pointtab_note_goodstypeb));
								break;
							case 2:
								mapnote.put(
										BasicProperties.XML_TAG110,
										getString(R.string.pointtab_note_goodstypec));
								break;
							}
						} else if (BasicProperties.XML_TAG111.equals(nodeName)) {
							
							String pricetext = xpp.nextText();
							
							mapnote.put(
									BasicProperties.XML_TAG111,
									pricetext
											+ " "
											+ getString(R.string.ad_list_fen));
							mapnote.put(BasicProperties.XML_TAG120,
									pricetext);
						} else if (BasicProperties.XML_TAG112.equals(nodeName)) {
							mapnote.put(BasicProperties.XML_TAG112,
									xpp.nextText());
						} else if (BasicProperties.XML_TAG113.equals(nodeName)) {
							String reg_day = xpp.nextText().substring(0, 10);
							mapnote.put(BasicProperties.XML_TAG113, reg_day);
						} else if (BasicProperties.XML_TAG114.equals(nodeName)) {
							mapnote.put(BasicProperties.XML_TAG114,
									xpp.nextText());
						} else if (BasicProperties.XML_TAG115.equals(nodeName)) {
							mapnote.put(BasicProperties.XML_TAG115,
									xpp.nextText());
						} else if (BasicProperties.XML_TAG116.equals(nodeName)) {
							mapnote.put(BasicProperties.XML_TAG116,
									xpp.nextText());
						} else if (BasicProperties.XML_TAG117.equals(nodeName)) {
							mapnote.put(BasicProperties.XML_TAG117,
									xpp.nextText());
						} else if (BasicProperties.XML_TAG118.equals(nodeName)) {
							mapnote.put(BasicProperties.XML_TAG118,
									xpp.nextText());
						} else if (BasicProperties.XML_TAG119.equals(nodeName)) {
							mapnote.put(BasicProperties.XML_TAG119,
									xpp.nextText());
						} else if (BasicProperties.XML_TAG1201.equals(nodeName)) {
							mapnote.put(BasicProperties.XML_TAG1201,
									xpp.nextText());
						} else if (BasicProperties.XML_TAG1202.equals(nodeName)) {
							mapnote.put(BasicProperties.XML_TAG1202,
									xpp.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							list.add(mapnote);
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
			case 8:
				tabcontent_point_lin_note.setAdapter(sa);

				break;

			}
		};
	};
	
	private OnItemClickListener icl = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			final HashMap<String, Object> map = (HashMap<String, Object>) tabcontent_point_lin_note
					.getItemAtPosition(arg2);

			if (goodstype == 0) {
				
				Intent intent = new Intent();
				intent.setClass(ELAD_buy.this,
						PiontDetailed.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("textviewa", map.get(BasicProperties.XML_TAG110).toString());
				bundle.putString("textviewb", map.get(BasicProperties.XML_TAG112).toString());
				bundle.putString("textviewc", map.get(BasicProperties.XML_TAG120).toString() + " " + getString(R.string.buy_yuan));
				bundle.putString("textviewd", map.get(BasicProperties.XML_TAG119).toString());
				bundle.putString("textviewe", Integer.parseInt(map.get(BasicProperties.XML_TAG120).toString()) * Integer.parseInt(map.get(
						BasicProperties.XML_TAG119)
						.toString())+Integer.parseInt(map.get(BasicProperties.XML_TAG1202)
								.toString())
						* Integer.parseInt(map.get(
								BasicProperties.XML_TAG119)
								.toString()) + " "
		+ getString(R.string.buy_yuan));
				bundle.putString("textviewf", map.get(
						BasicProperties.XML_TAG113).toString());
				bundle.putString("textviewg", map.get(
						BasicProperties.XML_TAG114).toString());
				bundle.putString("textviewh", map.get(
						BasicProperties.XML_TAG115).toString());
				bundle.putString("textviewi", map.get(
						BasicProperties.XML_TAG116).toString());
				bundle.putString("textviewj", map.get(
						BasicProperties.XML_TAG117).toString());
				bundle.putString("textviewl", map.get(BasicProperties.XML_TAG1202).toString() + " " + getString(R.string.buy_yuan));
				intent.putExtras(bundle);
				startActivity(intent);
				
			} else if (goodstype == 1) {
	
			}
		}
	};
}
