package com.eltong.elad;

import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ELAD_Person extends Activity {
	
	private Button elad_point = null;
	private Button elad_coupon = null;
	private Button elad_buy = null;
	private Button elad_persona = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_person);
		elad_point = (Button) findViewById(R.id.elad_point);
		elad_coupon = (Button) findViewById(R.id.elad_coupon);
		elad_buy = (Button) findViewById(R.id.elad_buy);
		elad_persona = (Button) findViewById(R.id.elad_persona);
		
		elad_point.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Person.this, ELAD_point.class);
				startActivity(intent);
			}
		});
		
		elad_coupon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Person.this, ELAD_coupon.class);
				startActivity(intent);
			}
		});
		elad_buy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Person.this, ELAD_buy.class);
				startActivity(intent);
			}
		});
		
		elad_persona.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Person.this, ELAD_persona.class);
				startActivity(intent);
			}
		});
	}
}