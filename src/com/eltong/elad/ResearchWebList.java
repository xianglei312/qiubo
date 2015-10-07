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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResearchWebList extends Activity {
	private WebView wv_research = null;
	private ProgressDialog wait_dialog = null;
	private Button research_button_answer = null;
	private TextView texttitle = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_research_weblist);
		texttitle =(TextView) findViewById(R.id.research_textviewtitle);
		wv_research = (WebView) findViewById(R.id.wv_research);

		texttitle.setText(BasicProperties.title);

		research_button_answer =(Button) findViewById(R.id.research_button_answer);
		research_button_answer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(ResearchWebList.this,
						ResearchQuestionActivity.class);
				startActivity(intent);
				ResearchWebList.this.finish();
			}
		});
		
		wait_dialog = AppliedMethod.showWaitDialog(this);
		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("Research_IDX=");
				strParam.append(BasicProperties.Research_IDX);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetResearchHTML,
						strParam.toString());
				doReadHTMLXML(is);
			}
		}.start();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			ResearchWebList.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				String research_content = (String) msg.obj;
				wv_research.getSettings().setSupportZoom(true);
				wv_research.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
				wv_research.setWebChromeClient(new WebChromeClient() {
					@Override
					public void onProgressChanged(WebView view, int newProgress) {
						// TODO Auto-generated method stub
						//if (newProgress == 100) {
							//handler.sendEmptyMessage(1);
						//}
						super.onProgressChanged(view, newProgress);
					}

				});
				wv_research.loadData(research_content, "text/html; charset=UTF-8",null);
				wait_dialog.dismiss();
				break;
			}
		};
	};
	
	/**
	 * @author XXD
	 * @note 闁荤姴娲╅褑銇愰崶顒�倞闁告挆鍐炬毈XML
	 */
	public void doReadHTMLXML(InputStream is) {
		if (is == null) {
			Message msg = new Message();
			msg.what = 0;
			msg.obj = this.getString(R.string.toast_msg_request_error);
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
						if (BasicProperties.XML_TAG93.equals(nodeName)) {
						
							String research_content = xpp.nextText();
								Message msg = new Message();
								msg.what = 3;
								msg.obj = research_content;
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
	
	/**
	 * @author XXD
	 * @note 闁诲繐绻愰幑鍒祊utStream闁哄鍎愰崜姘暦閺屻儱绠ｉ柟顒傛箿ring闂佸搫鍊介‖鍐兜閿燂拷	 */
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
