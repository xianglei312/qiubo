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

public class ELAD_AD_List extends Activity {
	
	private ListView tabcontent_qubeon_lv_ad = null;
	private List<MapListImageAndTextAD> lista = null;
	MapListImageAndTextListAdapterAD adapter =null;
//	private ProgressDialog wait_dialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_ad_list);
		
		tabcontent_qubeon_lv_ad = (ListView) findViewById(R.id.tabcontent_qubeon_lv_ad);
		getADListBylistADtype();
		
		tabcontent_qubeon_lv_ad.setOnItemClickListener(icad);

	}
	
	/**
	 * @author XXD
	 * @note 闁兼儳鍢茶ぐ鍥嵁閸喗鍟為柛鎺擃殸閵嗗啴鏁嶇仦鍊熷珯闁告梻濮撮崣鍝僫stView
	 */
	private void getADListBylistADtype() {
    	new Thread() {
    		public void run() {
		StringBuffer strParam = new StringBuffer();
		strParam.append("member_IDX=");
		strParam.append(BasicProperties.MEMBER_ID);
		strParam.append("&Ad_type=");
		strParam.append(BasicProperties.Ad_type);
		InputStream is = AppliedMethod.doPostSubmit(
				BasicProperties.HTTP_URL_getADList, strParam.toString());

		doReadListXML(is);
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessage(msg);
    		}
    	}.start();
		
	}
	
	private OnItemClickListener icad = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			// TODO Auto-generated method stub
//			wait_dialog = AppliedMethod.showWaitDialog(ELAD_AD_List.this);			
//	    	new Thread() {
//	    		public void run() {
//			StringBuffer strParam = new StringBuffer();
//			strParam.append("AD_IDX=");
//			strParam.append(lista.get(arg2).getad_IDX());
//			InputStream is = AppliedMethod.doPostSubmit(
//					BasicProperties.HTTP_URL_STATUS, strParam.toString());
//			int count = doReadCheckXML(is);
//			if (count > 0) {
//				if (getString(R.string.lv_ad_media_type_value).equals(
//						lista.get(arg2).getlist_ad_type())) {
			
					Intent intent = new Intent();
					intent.putExtra(BasicProperties.INTENT_KEY2, lista.get(arg2)
							.getvideo_name());
					intent.putExtra(BasicProperties.INTENT_KEY3, lista.get(arg2)
							.getad_IDX());
					intent.setClass(ELAD_AD_List.this,
							VideoPlayerActivity.class);
					startActivity(intent);
					ELAD_AD_List.this.finish();
					
//				} else if (getString(R.string.lv_ad_media_type_value2).equals(
//						lista.get(arg2).getlist_ad_type())) {
//					Intent intent = new Intent();
//					intent.putExtra(BasicProperties.INTENT_KEY3, lista.get(arg2)
//							.getad_IDX());
//					intent.setClass(TabSegmentADActivity.this,
//							WebpageActivity.class);
//					startActivity(intent);
//				}
//			} else if (count == 0) {
//				Message msg = new Message();
//				msg.what = 0;
//				msg.obj = getString(R.string.toast_msg_canread);
//				handler.sendMessage(msg);
//			}
//	    		}
//	    	}.start();
	    	//wait_dialog.dismiss();
		}
	};
	
	/**
	 * @author XXD
	 * @note 婵☆偓鎷烽悡锟犵嵁閸喗鍟為柣妯垮煐閿燂拷
	 */
	private int doReadCheckXML(InputStream is) {
		int count = -1;
		if (is == null) {
			Message msg = new Message();
			msg.what = 0;
			msg.obj = this.getString(R.string.toast_msg_request_error);
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
						if (BasicProperties.XML_TAG101.equals(nodeName)) {
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

	
	/**
	 * @author XXD
	 * @note 閻犲洩顕цぐ鍥嵁閸喗鍟為柛鎺擃殸閵嗗儣ML
	 */
	public void doReadListXML(InputStream is) {
		if (is == null) {
			Message msg = new Message();
			msg.what = 0;
			msg.obj = this.getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				MapListImageAndTextAD mapadlist = null;
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(is, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						lista = new ArrayList<MapListImageAndTextAD>();
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							mapadlist = new MapListImageAndTextAD();
						} else if (BasicProperties.XML_TAG6.equals(nodeName)) {
							mapadlist.setad_IDX(xpp.nextText());
						} else if (BasicProperties.XML_TAG7.equals(nodeName)) {
							String strType = xpp.nextText();
							if (BasicProperties.XML_VALUE4.equals(strType)) {
								mapadlist
										.setlist_ad_type(getString(R.string.lv_ad_media_type_value));
							} else if (BasicProperties.XML_VALUE5
									.equals(strType)) {
								mapadlist
										.setlist_ad_type(getString(R.string.lv_ad_media_type_value2));
							}
						} else if (BasicProperties.XML_TAG8.equals(nodeName)) {
							mapadlist.setlist_ad_title(xpp.nextText());
						} else if (BasicProperties.XML_TAG10.equals(nodeName)) {
							mapadlist.setlist_ad_point(xpp.nextText() + " "
									+ getString(R.string.ad_list_fen));
						} else if (BasicProperties.XML_TAG11.equals(nodeName)) {
							mapadlist.setvideo_pic(xpp.nextText());
						} else if (BasicProperties.XML_TAG12.equals(nodeName)) {
							mapadlist.setvideo_name(xpp.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						if (BasicProperties.XML_TAG5.equals(nodeName)) {
							lista.add(mapadlist);
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
			case 1:
				adapter = new MapListImageAndTextListAdapterAD(
						ELAD_AD_List.this, lista, tabcontent_qubeon_lv_ad);

				tabcontent_qubeon_lv_ad.setAdapter(adapter);
    			break;
			}
		};
	};
}

