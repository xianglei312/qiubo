package com.eltong.elad;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.Toast;

public class ELAD_read_List extends Activity {
	
	private ListView tabcontent_point_lin_ad = null;
	private List<MapListImageAndTextADre> listad = null;
	MapListImageAndTextListAdapterADre saad =null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_read_list);
		
		tabcontent_point_lin_ad = (ListView)findViewById(R.id.tabcontent_point_lin_ada);

		getADHistoryList();
		
		tabcontent_point_lin_ad.setOnItemClickListener(ic2);
	}
	
	private void getADHistoryList() {
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetADRecordHistory,
						strParam.toString());
				doReadADHistoryListXML(is);

				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	public void doReadADHistoryListXML(InputStream is) {
		if (is == null) {
			Message msg = new Message();
			msg.what = 0;
			msg.obj = getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				MapListImageAndTextADre mapad = null;
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(is, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						listad = new ArrayList<MapListImageAndTextADre>();
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							mapad = new MapListImageAndTextADre();
						} else if (BasicProperties.XML_TAG121.equals(nodeName)) {
							mapad.setamount(
									getString(R.string.buy_depiont)
											+ " "
											+ xpp.nextText()
											+ " " + getString(R.string.buy_fen));
						} else if (BasicProperties.XML_TAG122.equals(nodeName)) {
							mapad.setad_IDX(xpp.nextText());
						} else if (BasicProperties.XML_TAG123.equals(nodeName)) {
							mapad.setmember_quota(getString(R.string.buy_gongpiont)
											+ " "
											+ xpp.nextText()
											+ " " + getString(R.string.buy_fen));
						} else if (BasicProperties.XML_TAG124.equals(nodeName)) {

							int adtype = Integer.parseInt(xpp.nextText());
							switch (adtype) {
							case 0:
								mapad.setmedia_type(getString(R.string.lv_ad_media_type_value));
								break;
							case 1:
								mapad.setmedia_type(getString(R.string.lv_ad_media_type_value2));
								break;
							}
						} else if (BasicProperties.XML_TAG125.equals(nodeName)) {
							mapad.settitle(xpp.nextText());
						} else if (BasicProperties.XML_TAG126.equals(nodeName)) {
							mapad.setvideo_pic(xpp.nextText());
						} else if (BasicProperties.XML_TAG127.equals(nodeName)) {
							mapad.setbuy_viewFlag(xpp.nextText());
						} else if (BasicProperties.XML_TAG128.equals(nodeName)) {
							mapad.setbooking_viewFlag(xpp.nextText());
						} else if (BasicProperties.XML_TAG129.equals(nodeName)) {
							mapad.setconsult_viewFlag(xpp.nextText());
						} else if (BasicProperties.XML_TAG130.equals(nodeName)) {
							mapad.setcoupon_viewFlag(xpp.nextText());
						} else if (BasicProperties.XML_TAG131.equals(nodeName)) {
							mapad.setdelete_flag(xpp.nextText());
						} else if (BasicProperties.XML_TAG13101.equals(nodeName)) {
							mapad.setvideo_name(xpp.nextText());
						} else if (BasicProperties.XML_TAG13102.equals(nodeName)) {
							mapad.sethtml(xpp.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							listad.add(mapad);
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
	
	private OnItemClickListener ic2 = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			BasicProperties.ad_IDX = listad.get(arg2).getad_IDX();
			BasicProperties.video_pic = listad.get(arg2).getvideo_pic();
			BasicProperties.buy_viewFlag = listad.get(arg2).getbuy_viewFlag();
			BasicProperties.booking_viewFlag = listad.get(arg2).getbooking_viewFlag();
			BasicProperties.consult_viewFlag = listad.get(arg2).getconsult_viewFlag();
			BasicProperties.coupon_viewFlag = listad.get(arg2).getcoupon_viewFlag();
			BasicProperties.delete_flag =listad.get(arg2).getdelete_flag();
			BasicProperties.video_name = listad.get(arg2).getvideo_name();
			BasicProperties.html = listad.get(arg2).gethtml();
			BasicProperties.media_type = listad.get(arg2).getmedia_type();
			if (BasicProperties.delete_flag.equals("false")) {
				Intent intent = new Intent();
				intent.setClass(ELAD_read_List.this,
						ADHistoryDetailed.class);
				startActivity(intent);
			} else {
				Toast.makeText(ELAD_read_List.this,
						getString(R.string.pointtab_note_message),
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;

			case 1:
				saad = new MapListImageAndTextListAdapterADre(
						ELAD_read_List.this, listad, tabcontent_point_lin_ad);

				tabcontent_point_lin_ad.setAdapter(saad);

				break;

			}
		};
	};
}
