package com.eltong.elad;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignupUpdate extends Activity {
	
	private TextView settab_update_name = null;
	private Spinner spinner_update_age = null;
	private TextView settab_update_hp = null;
	private EditText settab_update_email = null;
	private Spinner settab_update_spinner_home_prov = null;
	private Spinner settab_update_spinner_home_city = null;
	private Spinner settab_update_spinner_home_area = null;

	private Button settab_update_button_submit = null;

	private String update_name = null;
	private String update_birthday = null;
	private String update_hp = null;
	private String update_email = null;
	private String update_spinner_home_prov = null;
	private String update_spinner_home_city = null;
	private String update_spinner_home_area = null;

	private ProgressDialog wait_dialog = null;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_signupupdate);

		settab_update_button_submit = (Button) findViewById(R.id.settab_update_button_submit);
		settab_update_button_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doSubmit();
			}
		});

		Bundle bundle = getIntent().getExtras();
		update_name = bundle.getString(BasicProperties.XML_TAG162);
		update_birthday =bundle.getString(BasicProperties.XML_TAG163);
		update_hp = bundle.getString(BasicProperties.XML_TAG178);
		update_email = bundle.getString(BasicProperties.XML_TAG175);
		update_spinner_home_prov = bundle.getString(BasicProperties.XML_TAG164);
		update_spinner_home_city = bundle.getString(BasicProperties.XML_TAG165);
		update_spinner_home_area = bundle.getString(BasicProperties.XML_TAG166);

		settab_update_name = (TextView) findViewById(R.id.settab_update_name);
		settab_update_name.setText(update_name);

		settab_update_hp = (TextView) findViewById(R.id.settab_update_hp);
		settab_update_hp.setText(update_hp);
		
		settab_update_email = (EditText) findViewById(R.id.settab_update_email);
		settab_update_email.setText(update_email);

		spinner_update_age = (Spinner)findViewById(R.id.spinner_update_age);
		// 濡ょ姷鍋為幐宕囨閻愬搫绀嗘繝闈涙－閻庡爼鏌涢弽鍨
				List<String> ageList = new ArrayList<String>();
				
				for(int i = 19;i<81;i++ )
				{
					String ageString = i+"";
					ageList.add(ageString);
				}
				int indexitemage = -1;
				for (int i = 0; i < ageList.size(); i++) {
					if (ageList.get(i).equals(update_birthday)) {
						indexitemage = i;
						break;
					}
				}
				ArrayAdapter<String> age = new ArrayAdapter<String>(
						this, android.R.layout.simple_spinner_item, ageList);
				age.setDropDownViewResource(R.layout.elad_simple_dropdown_item_1line);
				spinner_update_age.setAdapter(age);
				spinner_update_age.setSelection(indexitemage, true);
		
		settab_update_spinner_home_prov = (Spinner) findViewById(R.id.settab_update_spinner_home_prov);
		List<SpinnerKeyValue> list = doReadLocalXML(BasicProperties.XML_TAG13,
				BasicProperties.XML_TAG14, BasicProperties.XML_TAG15);
		int indexitem = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).value.equals(update_spinner_home_prov)) {
				indexitem = i;
				break;
			}
		}
		ArrayAdapter<SpinnerKeyValue> adapter = new ArrayAdapter<SpinnerKeyValue>(
				this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(R.layout.elad_simple_dropdown_item_1line);
		settab_update_spinner_home_prov.setAdapter(adapter);
		
		//if(!update_spinner_home_prov.trim().equals("")){
		settab_update_spinner_home_prov.setSelection(indexitem, true);
		//}
		
		settab_update_spinner_home_prov
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						List<SpinnerKeyValue> listcity = doReadLocalXML(
								BasicProperties.XML_TAG15,
								((SpinnerKeyValue) settab_update_spinner_home_prov
										.getSelectedItem()).id,
								BasicProperties.XML_TAG16);
						ArrayAdapter<SpinnerKeyValue> aa = new ArrayAdapter<SpinnerKeyValue>(
								SignupUpdate.this,
								android.R.layout.simple_spinner_item, listcity);
						aa.setDropDownViewResource(R.layout.elad_simple_dropdown_item_1line);
						settab_update_spinner_home_city.setAdapter(aa);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		settab_update_spinner_home_city = (Spinner) findViewById(R.id.settab_update_spinner_home_city);

		List<SpinnerKeyValue> lista = doReadLocalXML(BasicProperties.XML_TAG15,
				((SpinnerKeyValue) settab_update_spinner_home_prov
						.getSelectedItem()).id, BasicProperties.XML_TAG16);
		int indexitema = -1;
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).value.equals(update_spinner_home_city)) {
				indexitema = i;
				break;
			}
		}
		ArrayAdapter<SpinnerKeyValue> aa = new ArrayAdapter<SpinnerKeyValue>(
				SignupUpdate.this, android.R.layout.simple_spinner_item, lista);
		aa.setDropDownViewResource(R.layout.elad_simple_dropdown_item_1line);
		settab_update_spinner_home_city.setAdapter(aa);
		settab_update_spinner_home_city.setSelection(indexitema, true);
		settab_update_spinner_home_city
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						List<SpinnerKeyValue> listarea = doReadLocalXMLheng(
								BasicProperties.XML_TAG16,
								((SpinnerKeyValue) settab_update_spinner_home_city
										.getSelectedItem()).id,
								BasicProperties.XML_TAG17);
						ArrayAdapter<SpinnerKeyValue> aa = new ArrayAdapter<SpinnerKeyValue>(
								SignupUpdate.this,
								android.R.layout.simple_spinner_item, listarea);
						aa.setDropDownViewResource(R.layout.elad_simple_dropdown_item_1line);
						settab_update_spinner_home_area.setAdapter(aa);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		settab_update_spinner_home_area = (Spinner) findViewById(R.id.settab_update_spinner_home_area);
		List<SpinnerKeyValue> listb = doReadLocalXMLheng(BasicProperties.XML_TAG16,
				((SpinnerKeyValue) settab_update_spinner_home_city
						.getSelectedItem()).id, BasicProperties.XML_TAG17);
		int indexitemb = -1;
		for (int i = 0; i < listb.size(); i++) {
			if (listb.get(i).value.equals(update_spinner_home_area)) {
				indexitemb = i;
				break;
			}
		}
		ArrayAdapter<SpinnerKeyValue> bb = new ArrayAdapter<SpinnerKeyValue>(
				SignupUpdate.this, android.R.layout.simple_spinner_item, listb);
		bb.setDropDownViewResource(R.layout.elad_simple_dropdown_item_1line);
		settab_update_spinner_home_area.setAdapter(bb);
		settab_update_spinner_home_area.setSelection(indexitemb, true);

	}

	/**
	 * @author XXD
	 * @note 闁荤姴娲╅褑銇愰崶顒佸剳濞达綇鎷烽柣銏╁灟缁诲湣L
	 */
	public List<SpinnerKeyValue> doReadLocalXML(String ptn, String pid,
			String tagName) {
		boolean flag = false;
		List<SpinnerKeyValue> list = new ArrayList<SpinnerKeyValue>();
		InputStream is = getResources().openRawResource(R.raw.kor_area);
		try {
			XmlPullParser xpp = Xml.newPullParser();
			xpp.setInput(is, BasicProperties.CODED_FORMAT);
			int eventCode = xpp.getEventType();
			outer: while (eventCode != XmlPullParser.END_DOCUMENT) {
				String nodeName = xpp.getName();
				switch (eventCode) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (ptn.equals(nodeName)
							&& pid.equals(xpp.getAttributeValue(1))) {
						flag = true;
					}
					if (flag && nodeName.equals(tagName)) {
						list.add(new SpinnerKeyValue(xpp.getAttributeValue(0),
								xpp.getAttributeValue(1)));
					}
					break;
				case XmlPullParser.END_TAG:
					if (flag == true && ptn.equals(nodeName)) {
						flag = false;
						break outer;
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
		return list;
	}
	
	/**
	 * @author XXD
	 * @note 闁荤姴娲╅褑銇愰崶顒�缂佲檧鍨
	 */
	public List<SpinnerKeyValue> doReadLocalXMLheng(String ptn, String pid,
			String tagName) {
		boolean flag = false;
		List<SpinnerKeyValue> list = new ArrayList<SpinnerKeyValue>();
		InputStream is = getResources().openRawResource(R.raw.kor_area);
		try {
			XmlPullParser xpp = Xml.newPullParser();
			xpp.setInput(is, BasicProperties.CODED_FORMAT);
			int eventCode = xpp.getEventType();
			outer: while (eventCode != XmlPullParser.END_DOCUMENT) {
				String nodeName = xpp.getName();
				switch (eventCode) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (ptn.equals(nodeName)
							&& pid.equals(xpp.getAttributeValue(1))) {
						flag = true;
					}
					if (flag && nodeName.equals(tagName)) {
						list.add(new SpinnerKeyValue(xpp.getAttributeValue(0),
								xpp.getAttributeValue(0)));
					}
					break;
				case XmlPullParser.END_TAG:
					if (flag == true && ptn.equals(nodeName)) {
						flag = false;
						break outer;
					}
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
		return list;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent();
			intent.setClass(SignupUpdate.this, ELAD_persona.class);
			startActivity(intent);
			SignupUpdate.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * @author XXD
	 * @note 婵犮垼鍩栧銊╁吹鎼达絿鐭欓悗锝庡亜缁茬懓霉濠у灝锟介柟渚垮姂瀵劑宕烽鐔告闂佸湱顥滃〒瑙勭┍婵犲洤绠�
	 */
	private void doSubmit() {
		if (doValidate()) {
			wait_dialog = AppliedMethod.showWaitDialog(this);
			new Thread() {
				public void run() {
					StringBuffer strParam = new StringBuffer();
					strParam.append("member_IDX=");
					strParam.append(BasicProperties.MEMBER_ID);
					strParam.append("&user_name=");
					strParam.append(urlUtf(settab_update_name.getText().toString().trim()));
					strParam.append("&hp=");
					strParam.append(urlUtf(settab_update_hp.getText()
							.toString().trim()));
					strParam.append("&email=");
					strParam.append(urlUtf(settab_update_email.getText()
							.toString().trim()));
					strParam.append("&age=");
					strParam.append(urlUtf(spinner_update_age
							.getSelectedItem().toString()));
					strParam.append("&home_add_seng=");
					strParam.append(urlUtf(settab_update_spinner_home_prov
							.getSelectedItem().toString()));
					strParam.append("&home_add_si=");
					strParam.append(urlUtf(settab_update_spinner_home_city
							.getSelectedItem().toString()));
					strParam.append("&home_add_heen=");
					strParam.append(urlUtf(settab_update_spinner_home_area
							.getSelectedItem().toString()));

					InputStream is = AppliedMethod.doPostSubmit(
							BasicProperties.HTTP_URL_UpdateMember,
							strParam.toString());
					doReadUpdateMemberXML(is);
					wait_dialog.dismiss();
				}
			}.start();
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				wait_dialog.dismiss();
				Toast.makeText(getApplicationContext(),
						getString(R.string.settab_update_okaaa),
						Toast.LENGTH_SHORT).show();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				Intent intent = new Intent();
				intent.setClass(SignupUpdate.this, ELAD_persona.class);
				startActivity(intent);
				SignupUpdate.this.finish();
				break;
			case 2:	
				wait_dialog.dismiss();
				Toast.makeText(
						getApplicationContext(),
						getString(R.string.settab_update_erroraaa),
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	/**
	 * @author XXD
	 * @note 闂佸湱绮崝鎺戭灄閿旂晫鈹嶆い鏃囧Г閺嗩厼菐閸ワ絽澧插ù鐓庢嚇閻涱噣鎳滈悽闈涱棟闂佺顕栭崰姘叏閹间礁绠戝ù锝囨櫕閸庢煡寮堕敓锟� */
	public void doReadUpdateMemberXML(InputStream is) {
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
						if (BasicProperties.XML_TAG176.equals(nodeName)) {
							String UpdateMember = xpp.nextText();
							if (UpdateMember.equals("1")) {
								Message msg = new Message();
								msg.what = 1;
								handler.sendMessage(msg);
							} else {
								Message msg = new Message();
								msg.what = 2;
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

	/**
	 * @author XXD
	 * @note 闂佸湱绮崝鎺戭灄閿旂瓔姣ょ�锟藉暟濡诧拷
	 */
	private boolean doValidate() {
		if ("".equals(settab_update_name.getText().toString().trim())) {
			Toast.makeText(getApplicationContext(),
					this.getString(R.string.toast_msg_name), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	/**
	 * @author XXD
	 * @note 闁哄鍎愰崜姘暦閼碱剨鎷锋俊銈呭枤閸庡﹤鈽夐幘鐟扮处閻犳劗鍠栭崹鎯庨悿绂�8闂佺偨鍎茬换鍕椤忓牊鍎樺〒姘功缁�鎮归崶顒佹暠闁活亙鍗冲顕�嫉閻㈢數鍊為梺鍛婄懃閸婇攱绻涢崶顒佸仺?
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
