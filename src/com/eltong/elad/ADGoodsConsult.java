package com.eltong.elad;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.RTalk.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class ADGoodsConsult extends Activity {
	
	private ProgressDialog wait_dialog = null;
	
	private EditText edittexta;
	private EditText edittextb;
	private EditText edittextc;
	private EditText edittextd;
	private EditText edittexte;
	private EditText edittextf;
	
	private Button Buttonfdate;
	private Button Buttonftime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_goods_consult);
		Log.i("ADGoodsConsult", "onCreate");
		
		Buttonfdate = (Button) findViewById(R.id.consult_selecta);
		Buttonfdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance(Locale.CHINA);
				DatePickerDialog dpd = new DatePickerDialog(
						ADGoodsConsult.this, dsl, calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH));
				dpd.show();
			}
		});	
		
		Buttonftime = (Button) findViewById(R.id.consult_selectb);
		Buttonftime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance(Locale.CHINA);
			    new TimePickerDialog(ADGoodsConsult.this, new TimePickerDialog.OnTimeSetListener() {
			        
			        @Override
			        public void onTimeSet(TimePicker view, int hourofday, int minu) {

			         //设置时间
						edittexte.setText(hourofday + ":" +minu);
			        }
			       }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
			       
			      }});

		edittexta =(EditText) findViewById(R.id.consult_edittexta);
		edittextb =(EditText) findViewById(R.id.consult_edittextb);
		edittextc =(EditText) findViewById(R.id.consult_edittextc);
		edittextd =(EditText) findViewById(R.id.consult_edittextd);
		edittexte =(EditText) findViewById(R.id.consult_edittexte);
		edittextf =(EditText) findViewById(R.id.consult_edittextf);
		
		Button button_submit =(Button) findViewById(R.id.consult_button_submit);
		button_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				doSubmit();
			}
		});
	}
	
	/**
	 * @author XXD
	 * @note 多线程提�?
	 */
	private void doSubmit() {
		if (doValidate()) {
			wait_dialog = AppliedMethod.showWaitDialog(this);
			new Thread() {
				public void run() {
					StringBuffer strParam = new StringBuffer();
					strParam.append("member_IDX=");
					strParam.append(BasicProperties.MEMBER_ID);
					strParam.append("&AD_IDX=");
					strParam.append(BasicProperties.content_id);
					strParam.append("&Consult_day=");
					strParam.append(urlUtf((edittextd.getText().toString()+ " " + edittexte.getText().toString())));
					strParam.append("&title=");
					strParam.append(urlUtf(edittexta.getText().toString()));
					strParam.append("&Consult_content=");
					strParam.append(urlUtf(edittextf.getText().toString()));
					strParam.append("&name=");
					strParam.append(urlUtf(edittextb.getText().toString()));
					strParam.append("&tele=");
					strParam.append(edittextc.getText().toString());
					InputStream is = AppliedMethod.doPostSubmit(
							BasicProperties.HTTP_URL_CONSULT,
							strParam.toString());
					doReadAddBookingXML(is);
					wait_dialog.dismiss();
				}
			}.start();
		}
	}
	
	/**
	 * @author XXD
	 * @note 提交验证
	 */
	private boolean doValidate() {
		if ("".equals(edittexta.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.booking_textviewa_yz), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if ("".equals(edittextb.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.booking_textviewb_yz), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if ("".equals(edittextc.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.booking_textviewc_yz), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if ("".equals(edittextd.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.booking_textviewd_yz), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if ("".equals(edittexte.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.booking_textviewe_yz), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if ("".equals(edittextf.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.booking_textviewf_yz), Toast.LENGTH_SHORT)
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
	 * @note 读取注册XML并跳�?
	 */
	public void doReadAddBookingXML(InputStream is) {
		Message msg = new Message();
		if (is == null) {
			msg.what = 0;
			msg.obj = this.getString(R.string.toast_msg_request_error);
			handler.sendMessage(msg);
		} else {
			try {
				String testString = convertStreamToString(is);
				String resultString = testString.replaceAll("\n", "");
				InputStream iiss = new ByteArrayInputStream(resultString.getBytes()); 
				XmlPullParser xpp = Xml.newPullParser();
				xpp.setInput(iiss, BasicProperties.CODED_FORMAT);
				int eventCode = xpp.getEventType();
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					String nodeName = xpp.getName();
					switch (eventCode) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if (BasicProperties.XML_TAG68.equals(nodeName)) {
							String AddBooking = xpp.nextText();
							if (AddBooking.equals("1")) {
								msg.what = 1;
								msg.obj = this
										.getString(R.string.consult_request_right);
								handler.sendMessage(msg);						
								
//								Intent intent = new Intent();
//								intent.setClass(ADGoodsConsult.this,QuestionResultActivity.class);
//								startActivity(intent);
								overridePendingTransition(R.anim.push_left_in,
										R.anim.push_left_out);
								ADGoodsConsult.this.finish();
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
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Toast toast = null;
			switch (msg.what) {
			case 0:
				toast = Toast.makeText(getApplicationContext(),
						msg.obj.toString(),
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				   toast.show();
				break;
			case 1:
				toast = Toast.makeText(getApplicationContext(),
						msg.obj.toString(),
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				   toast.show();
				break;
			case 2:
				toast = Toast.makeText(getApplicationContext(),
						msg.obj.toString(),
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				   toast.show();
				break;
			}
		}
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
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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
	
	private DatePickerDialog.OnDateSetListener dsl = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month,
				int dayOfMonth) {
			edittextd.setText(year + "-" + fillGap(month + 1) + "-"
					+ fillGap(dayOfMonth));
		}
	};
	
	/**
	 * @author XXD
	 * @note 日期补位
	 */
	private String fillGap(int number) {
		if (number < 10) {
			return "0" + number;
		} else {
			return String.valueOf(number);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			ADGoodsConsult.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
