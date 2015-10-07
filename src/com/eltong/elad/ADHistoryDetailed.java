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
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ADHistoryDetailed extends Activity {
	private ImageView imgview;
	private Button button_buy = null;
	private Button button_booking = null;
	private Button button_consult = null;
	private Button button_coupon = null;
	private Button adhistorydetailed_replay = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_adhistory_detailed);
		imgview = (ImageView) findViewById(R.id.adhistorydetailed_imageView1);

		button_buy = (Button) findViewById(R.id.adhistorydetailed_buy);
		if (BasicProperties.buy_viewFlag.equals("true")) {
			button_buy.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method
					// stub
					Intent intent = new Intent(ADHistoryDetailed.this,
							ADGoodsBuyHistory.class);
					intent.putExtra(BasicProperties.INTENT_KEY3,
							BasicProperties.ad_IDX);
					startActivity(intent);
				}
			});
		}else{
			button_buy.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method
					// stub
					Toast.makeText(ADHistoryDetailed.this,
							R.string.toast_msg_buy,
							Toast.LENGTH_SHORT).show();
				}
			});
			
		}

		button_booking = (Button) findViewById(R.id.adhistorydetailed_booking);
		if (BasicProperties.booking_viewFlag.equals("true")) {
			button_booking.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method
					// stub
					Intent intent = new Intent(ADHistoryDetailed.this,
							ADGoodsBookingHistory.class);
					startActivity(intent);
				}
			});
		}else{
			button_booking.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method
					// stub
					Toast.makeText(ADHistoryDetailed.this,
							R.string.toast_msg_booking,
							Toast.LENGTH_SHORT).show();
				}
			});
			
		}

		button_consult = (Button) findViewById(R.id.adhistorydetailed_consult);
		if (BasicProperties.consult_viewFlag.equals("true")) {
			button_consult.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method
					// stub
					Intent intent = new Intent(ADHistoryDetailed.this,
							ADGoodsConsultHistory.class);
					startActivity(intent);
				}
			});
		}else{
			button_consult.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method
					// stub
					Toast.makeText(ADHistoryDetailed.this,
							R.string.toast_msg_consult,
							Toast.LENGTH_SHORT).show();
				}
			});

		}

		button_coupon = (Button) findViewById(R.id.adhistorydetailed_coupon);
		
		adhistorydetailed_replay = (Button) findViewById(R.id.adhistorydetailed_replay);
		adhistorydetailed_replay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method
				// stub
				if(BasicProperties.media_type.equals(getString(R.string.lv_ad_media_type_value))){
				Intent intent = new Intent();
				intent.setClass(ADHistoryDetailed.this,
						VideoReplay.class);
				startActivity(intent);
				}else if(BasicProperties.media_type.equals(getString(R.string.lv_ad_media_type_value2))){
					Intent intent = new Intent();
					intent.setClass(ADHistoryDetailed.this,
							WebADReplay.class);
					startActivity(intent);
				}
			}
		});

		new Thread() {
			public void run() {			
				try {
					byte[] data = NetTool.getImage(BasicProperties.video_pic);
					Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
					
					Message msg = new Message();
					msg.what = 2;
					msg.obj = bm;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				strParam.append("&AD_IDX=");
				strParam.append(BasicProperties.ad_IDX);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetCouponBox,
						strParam.toString());
				doReadGetCouponBoxXML(is);
			}
		}.start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				new AlertDialog.Builder(ADHistoryDetailed.this)
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
				final String GetCouponBox = (String) msg.obj;

				button_coupon.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method
						// stub
						if (BasicProperties.coupon_viewFlag.equals("true")
								&& GetCouponBox.equals("0")) {
							Intent intent = new Intent(ADHistoryDetailed.this,
									ADGoodsCouponHistory.class);
							startActivity(intent);
							ADHistoryDetailed.this.finish();
						} else {
							Toast.makeText(
									getApplicationContext(),
									getString(R.string.toast_msg_coupon),
									Toast.LENGTH_SHORT).show();
						}
					}
				});

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
	 * @note 读取返回XML
	 */
	public void doReadGetCouponBoxXML(InputStream is) {
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
						if (BasicProperties.XML_TAG132.equals(nodeName)) {
							String GetCouponBox = xpp.nextText();
							Message msg = new Message();
							msg.what = 1;
							msg.obj = GetCouponBox;
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			ADHistoryDetailed.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * @author TRF
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
