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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionResultActivity extends Activity {

	private String content_id = null;
	private String points = "";
	private int anwserresult = 0;
	private Button button_buy = null;
	private Button button_booking = null;
	private Button button_consult = null;
	private Button button_coupon = null;
	private ImageView imgview;
	private TextView textviewa;
	private TextView textviewb;
	private TextView textviewc;
	private TextView textviewd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_question_select);
		//Log.i("QuestionResultActivity", "onCreate");

		content_id =""+BasicProperties.content_id;
		points = ""+BasicProperties.points;
		anwserresult = BasicProperties.anwserresult;
		
		textviewb=(TextView) findViewById(R.id.qqs_textviewb);
		textviewb.setText(""+anwserresult);
		textviewc=(TextView) findViewById(R.id.qqs_textviewc);
		textviewc.setText(""+points+getString(R.string.buy_fen));

		imgview =(ImageView) findViewById(R.id.imageView1);
		textviewa=(TextView) findViewById(R.id.qqs_textviewa);
		textviewd=(TextView) findViewById(R.id.qqs_textviewd);

		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				strParam.append("&AD_IDX=");
				strParam.append(content_id);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetAnswerResult,
						strParam.toString());
				doReadMainXML(is);
			}
		}.start();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent();
			intent.setClass(QuestionResultActivity.this,
					ELAD_AD_List.class);
			startActivity(intent);
			QuestionResultActivity.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
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
						if (BasicProperties.XML_TAG51.equals(nodeName)) {
							String buy_viewFlag = xpp.nextText();
							if (buy_viewFlag.equals(BasicProperties.XML_VALUE51)) {
								button_buy = (Button) findViewById(R.id.button_buy);
								button_buy.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												Intent intent = new Intent(
														QuestionResultActivity.this,
														ADGoodsBuy.class);
												intent.putExtra(BasicProperties.INTENT_KEY3, content_id);
												startActivity(intent);

											}
										});
							} else {
								button_buy = (Button) findViewById(R.id.button_buy);
								button_buy.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												Toast toast = Toast.makeText(QuestionResultActivity.this,
														R.string.toast_msg_buy,
														Toast.LENGTH_SHORT);
												toast.setGravity(Gravity.CENTER, 0, 0);
												   toast.show();
											}
										});
							}
						}

						if (BasicProperties.XML_TAG52.equals(nodeName)) {
							String booking_viewFlag = xpp.nextText();
							if (booking_viewFlag.equals(BasicProperties.XML_VALUE51)) {
								button_booking = (Button) findViewById(R.id.button_booking);
								button_booking.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												Intent intent = new Intent(
														QuestionResultActivity.this,
														ADGoodsBooking.class);
												startActivity(intent);

											}
										});
							} else {
								button_booking = (Button) findViewById(R.id.button_booking);
								button_booking.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												Toast toast = Toast.makeText(QuestionResultActivity.this,
														R.string.toast_msg_booking,
														Toast.LENGTH_SHORT);
												toast.setGravity(Gravity.CENTER, 0, 0);
												   toast.show();
											}
										});
							}
						}
						if (BasicProperties.XML_TAG53.equals(nodeName)) {
							String consult_viewFlag = xpp.nextText();
							if (consult_viewFlag.equals(BasicProperties.XML_VALUE51)) {
								button_consult = (Button) findViewById(R.id.button_consult);
								button_consult.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												Intent intent = new Intent(
														QuestionResultActivity.this,
														ADGoodsConsult.class);
												startActivity(intent);

											}
										});
							} else {
								button_consult = (Button) findViewById(R.id.button_consult);
								button_consult.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												Toast toast = Toast.makeText(QuestionResultActivity.this,
														R.string.toast_msg_consult,
														Toast.LENGTH_SHORT);
												toast.setGravity(Gravity.CENTER, 0, 0);
												   toast.show();
											}
										});
							}
						}
						if (BasicProperties.XML_TAG54.equals(nodeName)) {
							String coupon_viewFlag = xpp.nextText();
							if (coupon_viewFlag.equals(BasicProperties.XML_VALUE51)) {
								button_coupon = (Button) findViewById(R.id.button_coupon);
								button_coupon.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												Intent intent = new Intent(
														QuestionResultActivity.this,
														ADGoodsCoupon.class);
												startActivity(intent);

											}
										});
							} else {
								button_coupon = (Button) findViewById(R.id.button_coupon);
								button_coupon.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method
												// stub
												Toast toast = Toast.makeText(QuestionResultActivity.this,
														R.string.toast_msg_coupon,
														Toast.LENGTH_SHORT);
												toast.setGravity(Gravity.CENTER, 0, 0);
												   toast.show();
											}
										});
							}
						}
						
						if (BasicProperties.XML_TAG57.equals(nodeName)) {
							String video_pic = xpp.nextText();

							try {
								byte[] data = NetTool.getImage(video_pic);
								Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
								Message msg = new Message();
								msg.what = 1;
								msg.obj = bm;
								handler.sendMessage(msg);
								
							} catch (Exception e) {
								// TODO: handle exception
								Log.i("video_pic", e.getMessage());
								Toast.makeText(this, R.string.EConponDownload_image_error, 1).show();
							}
						}
						
						if (BasicProperties.XML_TAG56.equals(nodeName)) {
							String member_quota = xpp.nextText();
							Message msg = new Message();
							msg.what = 2;
							msg.obj = member_quota;
							handler.sendMessage(msg);
						}
						
						if (BasicProperties.XML_TAG55.equals(nodeName)) {
							String money = xpp.nextText();
							Message msg = new Message();
							msg.what = 3;
							msg.obj = money;
							handler.sendMessage(msg);
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
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				new AlertDialog.Builder(QuestionResultActivity.this)
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
				Bitmap bm = (Bitmap) msg.obj;
				imgview.setImageBitmap(bm);
				break;
				
			case 2:
				String quota = (String) msg.obj;
				textviewa.setText(""+quota+getString(R.string.buy_fen));
				break;
				
			case 3:
				String money = (String) msg.obj;
				textviewd.setText(""+money+getString(R.string.buy_fen));
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
}
