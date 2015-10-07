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

public class ELAD_RS_List extends Activity {
	
	private ListView tabcontent_qubeon_lin_inquiry = null;
	private List<MapListImageAndTextRS> listr = null;
	MapListImageAndTextListAdapterRS adapterrs =null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_rs_list);
		
		tabcontent_qubeon_lin_inquiry = (ListView)findViewById(R.id.tabcontent_qubeon_lin_inquiry);
		getResearchList();
		tabcontent_qubeon_lin_inquiry.setOnItemClickListener(icrs);
	}
	
	private void getResearchList() {
    	new Thread() {
    		public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_ResearchLIST, strParam.toString());
				doReadResearchListXML(is);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
    		}
    	}.start();
		
	}
	
	public void doReadResearchListXML(InputStream is) {
		if (is == null) {
			Message msg = new Message();
			msg.what = 0;
			msg.obj = getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				MapListImageAndTextRS maprslist = null;
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(is, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						listr = new ArrayList<MapListImageAndTextRS>();
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG72.equals(nodeName)) {
							maprslist = new MapListImageAndTextRS();
						} else if (BasicProperties.XML_TAG73.equals(nodeName)) {
							maprslist.setResearch_IDX(xpp.nextText());
						} else if (BasicProperties.XML_TAG74.equals(nodeName)) {
							maprslist.setmember_quota("" + xpp.nextText() + " "
									+ getString(R.string.ad_list_fen));
						} else if (BasicProperties.XML_TAG75.equals(nodeName)) {
							maprslist.settitle(xpp.nextText());
						} else if (BasicProperties.XML_TAG76.equals(nodeName)) {
							maprslist.setimages(xpp.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							listr.add(maprslist);
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
	
	private OnItemClickListener icrs = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			// TODO Auto-generated method stub
	    	new Thread() {
	    		public void run() {
			StringBuffer strParam = new StringBuffer();
			strParam.append("Research_IDX=");
			strParam.append(listr.get(arg2).getResearch_IDX());
			InputStream is = AppliedMethod.doPostSubmit(
					BasicProperties.HTTP_URL_ResearchSTATUS,
					strParam.toString());
			int count = doReadResearchCheckXML(is);
			if (count > 0) {
				BasicProperties.member_quota = listr.get(arg2)
						.getmember_quota();
				BasicProperties.images = listr.get(arg2).getimages();
				BasicProperties.title = listr.get(arg2).gettitle();
				BasicProperties.Research_IDX = Integer.parseInt(listr.get(arg2)
						.getResearch_IDX());
				Intent intent = new Intent();
				intent.setClass(ELAD_RS_List.this,
				ResearchWebList.class);
				startActivity(intent);
				ELAD_RS_List.this.finish();
			} else if (count == 0) {
				Message msg = new Message();
				msg.what = 0;
				msg.obj = getString(R.string.toast_msg_canread);
				handler.sendMessage(msg);
				getResearchList();
			}
	    		}
	    	}.start();
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
				adapterrs = new MapListImageAndTextListAdapterRS(
						ELAD_RS_List.this, listr, tabcontent_qubeon_lin_inquiry);
				tabcontent_qubeon_lin_inquiry.setAdapter(adapterrs);
    			break;
			}
		};
	};
	
	private int doReadResearchCheckXML(InputStream is) {
		int count = -1;
		if (is == null) {
			Message msg = new Message();
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
						if (BasicProperties.XML_TAG103.equals(nodeName)) {
							return Integer.parseInt(xpp.nextText());
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
		return count;
	}
}
