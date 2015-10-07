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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class ResearchResultActivity extends Activity {
	private String Researchpoints = "";
	private ImageView imgview;
	private TextView textviewa;
	private TextView textviewb;
	private TextView textviewc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.elad_research_question_result);
		
		Researchpoints ="" + BasicProperties.Researchpoints;
		textviewa=(TextView) findViewById(R.id.research_textviewa);
		textviewa.setText(BasicProperties.member_quota);
		textviewb=(TextView) findViewById(R.id.research_textviewb);
		textviewb.setText(""+Researchpoints+" "+getString(R.string.buy_fen));
		
		imgview =(ImageView) findViewById(R.id.imageView1);

		textviewc=(TextView) findViewById(R.id.research_textviewc);
		
		new Thread() {
			public void run() {
				
				try {
					byte[] data = NetTool.getImage(BasicProperties.images);
					Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);

					Message msg = new Message();
					msg.what = 2;
					msg.obj = bm;
					handler.sendMessage(msg);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetMemberMoney,
						strParam.toString());
				doReadMainXML(is);
			}
		}.start();
		
	}
	
	/**
	 * @author XXD
	 * @note 读取返回XML
	 */
	public void doReadMainXML(InputStream is) {
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
						if (BasicProperties.XML_TAG92.equals(nodeName)) {
						
							String GetMemberMoney = xpp.nextText();
								Message msg = new Message();
								msg.what = 1;
								msg.obj = GetMemberMoney;
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
			switch (msg.what) {
			case 0:
				new AlertDialog.Builder(ResearchResultActivity.this)
						.setTitle(getString(R.string.ad_val_title2))
						.setMessage(getString(R.string.ad_val_message3))
						.setCancelable(false)
						.setPositiveButton(getString(R.string.ad_val_ok),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										android.os.Process
												.killProcess(android.os.Process
														.myPid());
									}
								}).show();
				break;
			case 1:
				String money = (String) msg.obj;
				textviewc.setText(""+money+" "+getString(R.string.buy_fen));
				break;
			case 2:
				Bitmap bm = (Bitmap) msg.obj;
				imgview.setImageBitmap(bm);
				break;
			}
		};
	};

	/**
	 * @author XXD
	 * @note 将InputStream转换成String方法
	 */
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			ResearchResultActivity.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
