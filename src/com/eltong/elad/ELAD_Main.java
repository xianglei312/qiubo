package com.eltong.elad;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ELAD_Main extends Activity {

	private Button elad_ad = null;
	private Button elad_person = null;
	private Button elad_re = null;
	private Button elad_qa = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_main);
		
//		Bundle bundle = getIntent().getExtras();
//		BasicProperties.Name = bundle.getString("name");
//		BasicProperties.MEMBER_ID =Integer.parseInt(bundle.getString("member_id"));
//		BasicProperties.Telephone = bundle.getString("telephone");
		
		doSubmit();
		
		elad_ad = (Button) findViewById(R.id.elad_ad);
		elad_person = (Button) findViewById(R.id.elad_person);
		elad_re = (Button) findViewById(R.id.elad_re);
		elad_qa = (Button) findViewById(R.id.elad_qa);

		elad_ad.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Main.this, ELAD_AD.class);
				startActivity(intent);
			}
		});
		
		elad_person.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Main.this, ELAD_Person.class);
				startActivity(intent);
			}
		});
		
		elad_re.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Main.this, ELAD_Re.class);
				startActivity(intent);
			}
		});
		
		elad_qa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Main.this, ELAD_Qa.class);
				startActivity(intent);
			}
		});
	}	
	
	private void doSubmit() {

			new Thread() {
				public void run() {
					StringBuffer strParam = new StringBuffer();

					strParam.append("&user_name=");
					strParam.append(urlUtf(BasicProperties.Name));
					strParam.append("&memberid=");
					strParam.append(BasicProperties.MEMBER_ID);
					strParam.append("&hp=");
					strParam.append(BasicProperties.Telephone);
					AppliedMethod.doPostSubmit(
							BasicProperties.HTTP_URL_SIGNUP,
							strParam.toString());
				}
			}.start();
	}
	
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
}
