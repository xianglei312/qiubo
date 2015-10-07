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
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ADGoodsCouponHistory extends Activity {
	
	private ImageView imgview;
	private TextView textviewa;
	private TextView textviewb;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_goods_coupon);
		Log.i("ADGoodsCoupon", "onCreate");
		imgview =(ImageView) findViewById(R.id.coupon_imageView);
		textviewa=(TextView) findViewById(R.id.coupon_texta);	
		textviewb=(TextView) findViewById(R.id.coupon_textb);

		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				strParam.append("&AD_IDX=");
				strParam.append(BasicProperties.ad_IDX);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetAD_coupon,
						strParam.toString());
				doReadGetCouponXML(is);
			}
		}.start();
	}
	
	/**
	 * @author XXD
	 * @note 读取返回XML
	 */
	public void doReadGetCouponXML(InputStream is) {
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
						if (BasicProperties.XML_TAG69.equals(nodeName)) {
							String path = xpp.nextText();
							try {
							byte[] data = NetTool.getImage(path);
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
							break;
						}
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
				new AlertDialog.Builder(ADGoodsCouponHistory.this)
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
				textviewa.setText(getString(R.string.coupon_textviewaa));
				textviewb.setText(getString(R.string.coupon_textviewbb));
				break;
			}
		};
	};
	
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			ADGoodsCouponHistory.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}

