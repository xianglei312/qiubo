package com.elt.elsms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.RTalk.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class XiaoXiWebPage extends Activity {

	private WebView wv_tuwenpage = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuwen_webpage);

		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("id=");
				strParam.append(BasicPropertiess.xiaoxi_IDX);
				InputStream is = AppliedMethod.doGetSubmit(BasicPropertiess.HTTP_URL_getHtmlXiangxi,
						strParam.toString());
				doReadHTMLXML(is);
			}
		}.start();
	}

	public void doReadHTMLXML(InputStream is) {
		if (is == null) {
			Message msg = new Message();
			msg.what = 0;
			msg.obj = this.getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				String testString = ConvertStreamToString.convertStreamToString(is);
				String resultString = testString.replaceAll("\n", "");
				InputStream iiss = new ByteArrayInputStream(resultString.getBytes());
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(iiss, BasicPropertiess.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if (BasicPropertiess.XML_TAG3009.equals(nodeName)) {

							String html = xpp.nextText();
							Message msg = new Message();
							msg.what = 2;
							msg.obj = html;
							handler.sendMessage(msg);
						}
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
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				break;
			case 2:

				String html = (String) msg.obj;
				wv_tuwenpage = (WebView) findViewById(R.id.wv_tuwenpage);
				wv_tuwenpage.getSettings().setSupportZoom(true);
				wv_tuwenpage.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
				wv_tuwenpage.setWebChromeClient(new WebChromeClient() {
					@Override
					public void onProgressChanged(WebView view, int newProgress) {
						// TODO Auto-generated method stub
						if (newProgress == 100) {
							handler.sendEmptyMessage(1);
						}
						super.onProgressChanged(view, newProgress);
					}

				});
				wv_tuwenpage.loadData(html, "text/html; charset=UTF-8", null);

				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			XiaoXiWebPage.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
