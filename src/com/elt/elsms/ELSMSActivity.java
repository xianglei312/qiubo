package com.elt.elsms;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.RTalk.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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

public class ELSMSActivity extends Activity {

	public static Uri mSmsUri = Uri.parse("content://sms/inbox");

	private SimpleAdapter xiaoxi = null;
	private ArrayList<HashMap<String, Object>> listxiaoxi = null;
	private ListView list_xiaoxi = null;

	public ProgressDialog pBar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// startService(new Intent(this, IconService.class));

		list_xiaoxi = (ListView) findViewById(R.id.list_xiaoxi);
		list_xiaoxi.setOnItemClickListener(ic);

		getxiaoxiList();

	}

	private OnItemClickListener ic = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub

			String typeString = listxiaoxi.get(arg2).get(BasicPropertiess.XML_TAG5).toString();
			if (typeString.equals("3")) {
				BasicPropertiess.xiaoxi_IDX = Integer
						.parseInt(listxiaoxi.get(arg2).get(BasicPropertiess.XML_TAG2).toString());
				Intent intent = new Intent();
				intent.setClass(ELSMSActivity.this, XiaoXiWebPage.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent();
				intent.putExtra("neirong", listxiaoxi.get(arg2).get(BasicPropertiess.XML_TAG3).toString());
				intent.setClass(ELSMSActivity.this, XiaoXiDetailed.class);
				startActivity(intent);
			}

		}
	};

	private void getxiaoxiList() {
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("tel=");
				strParam.append(BasicPropertiess.telephone);
				InputStream is = AppliedMethod.doGetSubmit(BasicPropertiess.HTTP_URL_Selectxiaoxi, strParam.toString());
				doReadxiaoxiXML(is);
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}.start();
	}

	public void doReadxiaoxiXML(InputStream is) {
		Message msg = new Message();
		if (is == null) {
			msg.what = 0;
			msg.obj = getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				HashMap<String, Object> map = null;
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(is, BasicPropertiess.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						listxiaoxi = new ArrayList<HashMap<String, Object>>();
						break;
					case XmlPullParser.START_TAG:
						if (BasicPropertiess.XML_TAG1.equals(nodeName)) {
							map = new HashMap<String, Object>();
						} else if (BasicPropertiess.XML_TAG2.equals(nodeName)) {
							map.put(BasicPropertiess.XML_TAG2, xpp.nextText());
						} else if (BasicPropertiess.XML_TAG3.equals(nodeName)) {
							map.put(BasicPropertiess.XML_TAG3, xpp.nextText());
						} else if (BasicPropertiess.XML_TAG4.equals(nodeName)) {
							map.put(BasicPropertiess.XML_TAG4, xpp.nextText());
						} else if (BasicPropertiess.XML_TAG5.equals(nodeName)) {
							map.put(BasicPropertiess.XML_TAG5, xpp.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicPropertiess.XML_TAG1.equals(nodeName)) {
							listxiaoxi.add(map);
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
				Toast.makeText(ELSMSActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
				break;
			case 1:
				xiaoxi = new SimpleAdapter(ELSMSActivity.this, listxiaoxi, R.layout.listitem_xiaoxi,
						new String[] { BasicPropertiess.XML_TAG3, BasicPropertiess.XML_TAG4 },
						new int[] { R.id.list_xiaoxi_content, R.id.list_xiaoxi_time });
				list_xiaoxi.setAdapter(xiaoxi);
				break;
			}

		}
	};

}