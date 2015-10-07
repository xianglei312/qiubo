package com.eltong.elad;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.RTalk.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ADGoodsBuy extends Activity {
	private int jine = 0;
	private int jinea = 0;
	private int yongyoujifen = 0;
	private ImageView imgview;
	private TextView textviewa;
	private TextView textviewb;
//	private TextView textviewc;
	private TextView textviewd;
	private TextView textviewe;
	private TextView textviewf;
	private RadioGroup radiogroup;
	private RadioButton radioButton;
	private EditText edittexta;
	private EditText edittextb;
	private EditText edittextc;
	private EditText edittextd;
	private EditText edittexte;
	private String pricesb;
	private TextView textviewtitle;
	private ProgressDialog wait_dialog = null;

	private String content_id = null;

	private int freight_typea = 0;
	private String yunfei = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_adgoods_buy);
		Log.i("ADGoodsBuy", "onCreate");
		imgview = (ImageView) findViewById(R.id.imageView_video_pic);
		textviewa = (TextView) findViewById(R.id.buy_textviewa);
		textviewb = (TextView) findViewById(R.id.buy_textviewb);
//		textviewc = (TextView) findViewById(R.id.buy_textviewc);
		textviewd = (TextView) findViewById(R.id.buy_textviewd);
		textviewe = (TextView) findViewById(R.id.buy_textviewe);
		textviewf = (TextView) findViewById(R.id.buy_textviewf);

		radiogroup = (RadioGroup) findViewById(R.id.buy_radiogroup);

		edittexta = (EditText) findViewById(R.id.buy_edittexta);
		edittextb = (EditText) findViewById(R.id.buy_edittextb);
		edittextc = (EditText) findViewById(R.id.buy_edittextc);
		edittextd = (EditText) findViewById(R.id.buy_edittextd);
		edittexte = (EditText) findViewById(R.id.buy_edittexte);
		
		textviewtitle = (TextView) findViewById(R.id.textviewtitle);

		Button button_submit = (Button) findViewById(R.id.buy_button_submit);
		button_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				doSubmit();
			}
		});

		Bundle bundle = getIntent().getExtras();
		content_id = bundle.getString(BasicProperties.INTENT_KEY3);

		new Thread() {
			public void run() {
				StringBuffer strParam = new StringBuffer();
				strParam.append("member_IDX=");
				strParam.append(BasicProperties.MEMBER_ID);
				strParam.append("&AD_IDX=");
				strParam.append(content_id);
				InputStream is = AppliedMethod.doPostSubmit(
						BasicProperties.HTTP_URL_GetAD_goods,
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
						if (BasicProperties.XML_TAG58.equals(nodeName)) {
							String image = xpp.nextText();
							try {
								byte[] data = NetTool.getImage(image);
								Bitmap bm = BitmapFactory.decodeByteArray(data,
										0, data.length);
								Message msg = new Message();
								msg.what = 1;
								msg.obj = bm;
								handler.sendMessage(msg);
							} catch (Exception e) {
								// TODO: handle exception
							}
							break;
						}

						if (BasicProperties.XML_TAG59.equals(nodeName)) {
							String goods_content = xpp.nextText();
							Message msg = new Message();
							msg.what = 2;
							msg.obj = goods_content;
							handler.sendMessage(msg);
							break;

						}
						if (BasicProperties.XML_TAG60.equals(nodeName)) {
							String money = xpp.nextText();
							Message msg = new Message();
							msg.what = 3;
							msg.obj = money;
							handler.sendMessage(msg);
							break;
						}
						if (BasicProperties.XML_TAG61.equals(nodeName)) {
							String market_price = xpp.nextText();
							Message msg = new Message();
							msg.what = 4;
							msg.obj = market_price;
							handler.sendMessage(msg);
							break;
						}
						if (BasicProperties.XML_TAG62.equals(nodeName)) {
							String price = xpp.nextText();
							Message msg = new Message();
							msg.what = 5;
							msg.obj = price;
							handler.sendMessage(msg);
							break;
						}
						if (BasicProperties.XML_TAG63.equals(nodeName)) {
							String freight_type = xpp.nextText();
							int freight_typea = Integer.parseInt(freight_type);
							if (freight_typea == 1) {
								Message msg = new Message();
								msg.what = 6;
								handler.sendMessage(msg);
							}
							break;
						}
						if (BasicProperties.XML_TAG64.equals(nodeName)) {
							String regular = xpp.nextText();
							Message msg = new Message();
							msg.what = 7;
							msg.obj = regular;
							handler.sendMessage(msg);
							break;
						}
						if (BasicProperties.XML_TAG65.equals(nodeName)) {
							String fast = xpp.nextText();
							Message msg = new Message();
							msg.what = 8;
							msg.obj = fast;
							handler.sendMessage(msg);
							break;
						}
						if (BasicProperties.XML_TAG66.equals(nodeName)) {
							String ems = xpp.nextText();
							Message msg = new Message();
							msg.what = 9;
							msg.obj = ems;
							handler.sendMessage(msg);
							break;
						}
						if (BasicProperties.XML_TAG70.equals(nodeName)) {
							String goods_name = xpp.nextText();
							Message msg = new Message();
							msg.what = 10;
							msg.obj = goods_name;
							handler.sendMessage(msg);
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
				new AlertDialog.Builder(ADGoodsBuy.this)
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
				String goods_content = (String) msg.obj;
				textviewa.setText(goods_content);
				break;

			case 3:
				String money = (String) msg.obj;
				yongyoujifen = Integer.parseInt(money);
				textviewb.setText(money + " " + getString(R.string.buy_fen));
//				textviewc.setText(money + " " + getString(R.string.buy_yuan));
				break;
			case 4:
				String market_price = (String) msg.obj;
				//Float market_pricea = Float.parseFloat(market_price);
				textviewd.setText(market_price
						+ getString(R.string.buy_yuan));
				break;
			case 5:
				String price = (String) msg.obj;
				pricesb = price;
				textviewe.setText(price+ getString(R.string.buy_yuan));
				break;
			case 6:
				freight_typea = 1;
				textviewf.setVisibility(View.VISIBLE);
				radiogroup.setVisibility(View.GONE);
				break;
			case 7:
				final String regular = (String) msg.obj;
				radioButton = getRadioButton();
				radioButton.setText(getString(R.string.buy_regular) + regular + getString(R.string.buy_yuan));
				radioButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						yunfei = regular;
					}
				});
				radiogroup.addView(radioButton);
				break;
			case 8:
				final String fast = (String) msg.obj;
				radioButton = getRadioButton();
				radioButton.setText(getString(R.string.buy_fast) + fast
						+ getString(R.string.buy_yuan));
				radioButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						yunfei = fast;
					}
				});
				radiogroup.addView(radioButton);
				break;
			case 9:
				final String ems = (String) msg.obj;
				radioButton = getRadioButton();
				radioButton.setText(getString(R.string.buy_ems) + ems
						+ getString(R.string.buy_yuan));
				radioButton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						yunfei = ems;
					}
				});
				radiogroup.addView(radioButton);
				break;
			case 10:
				String goods_name = (String) msg.obj;
				textviewtitle.setText(goods_name);
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

	/**
	 * @author XXD
	 * @note RadioButton实例
	 */
	private RadioButton getRadioButton() {
		RadioButton rb = new RadioButton(this);
		rb.setTextSize(BasicProperties.QUESTION_TEXT_SIZE);
		rb.setTextColor(getResources().getColor(R.color.silver));
		return rb;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			ADGoodsBuy.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * @author XXD
	 * @note 多线程提�?
	 */
	private void doSubmit() {
		if (doValidate()) {
			wait_dialog = AppliedMethod.showWaitDialog(this);
			jine = 0;
			jinea = 0;
			jinea += Integer.parseInt(pricesb);
			jine += Integer.parseInt(pricesb)
					* Integer.parseInt(edittexta.getText().toString());
			if (jine <= yongyoujifen) {
				new Thread() {
					public void run() {
						StringBuffer strParam = new StringBuffer();
						strParam.append("AD_IDX=");
						strParam.append(BasicProperties.content_id);
						strParam.append("&member_IDX=");
						strParam.append(BasicProperties.MEMBER_ID);
						strParam.append("&content_type=0");
						strParam.append("&price=");
						strParam.append("" + jinea);
						strParam.append("&title=");
						strParam.append(urlUtf(textviewtitle.getText()
								.toString()));
						strParam.append("&recipient_name=");
						strParam.append(urlUtf(edittextb.getText().toString()));
						strParam.append("&recipient_add=");
						strParam.append(urlUtf(edittextd.getText().toString()));
						strParam.append("&recipient_tel=");
						strParam.append(urlUtf(edittextc.getText().toString()));
						strParam.append("&memo=");
						strParam.append(urlUtf(edittexte.getText().toString()));
						strParam.append("&buy_number=");
						strParam.append(urlUtf(edittexta.getText().toString()));
						if (yunfei != null) {
							strParam.append("&freight=");
							strParam.append(yunfei);
						}else{
							strParam.append("&freight=");
							strParam.append("0");
						}				
						InputStream is = AppliedMethod.doPostSubmit(
								BasicProperties.HTTP_URL_InsertBuy,
								strParam.toString());
						doReadInsertBuyXML(is);
						wait_dialog.dismiss();
					}
				}.start();
			}else{
				Toast.makeText(getApplicationContext(),
						this.getString(R.string.buy_submit_error),
						Toast.LENGTH_SHORT).show();
				wait_dialog.dismiss();
			}
		}
	}

	/**
	 * @author XXD
	 * @note 提交验证
	 */
	private boolean doValidate() {
//		String strtele = edittextc.getText().toString().trim();
//		String regex = "1\\d{10}";
//		
		if (freight_typea == 0) {
			if (yunfei == null) {
				Toast.makeText(getApplicationContext(),
						this.getString(R.string.buy_freight),
						Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		if ("".equals(edittexta.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.buy_edittexta), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if ("0".equals(edittexta.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.buy_edittextaaa), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if ("".equals(edittextb.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.buy_edittextb), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if ("".equals(edittextc.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.buy_edittextc), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
//		if (!Pattern.matches(regex, strtele)) {
//			Toast.makeText(getApplicationContext(),
//					this.getString(R.string.booking_textviewc_yza),
//					Toast.LENGTH_SHORT).show();
//			return false;
//		}
		if ("".equals(edittextd.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.buy_edittextd), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	/**
	 * @author XXD
	 * @note 转换字符串为“UTF-8”编码，请求时传参使�?
	 */
	private String urlUtf(String text) {
		String urlUtf = "";
		try {
			urlUtf = URLEncoder.encode(text, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlUtf;
	}

	/**
	 * @author XXD
	 * @note 读取XML并跳�?
	 */
	public void doReadInsertBuyXML(InputStream is) {
		Message msg = new Message();
		if (is == null) {
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
						if (BasicProperties.XML_TAG71.equals(nodeName)) {
							String AddBooking = xpp.nextText();
							if (AddBooking.equals("1")) {
								// 购买商品数据库添加完成后的相关操作�?。�?。�?�?
								// 以下为临时代�?
//								Intent intent = new Intent();
//								intent.setClass(ADGoodsBuy.this,
//										QuestionResultActivity.class);
//								startActivity(intent);
								overridePendingTransition(R.anim.push_left_in,
										R.anim.push_left_out);
								ADGoodsBuy.this.finish();
							} else {
								msg.what = 2;
								msg.obj = this
										.getString(R.string.booking_request_error);
								handler.sendMessage(msg);
							}
						}
						break;
					case XmlPullParser.END_TAG:
						break;
					}
					eventCode = xpp.next();
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				wait_dialog.dismiss();
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				wait_dialog.dismiss();
				e.printStackTrace();
			}
		}
	}
}
