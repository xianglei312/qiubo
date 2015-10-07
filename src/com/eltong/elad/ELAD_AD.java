package com.eltong.elad;

import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ELAD_AD extends Activity {
	
	private Button elad_adad = null;
	private Button elad_reserch = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_ad);
		
		elad_adad = (Button) findViewById(R.id.elad_adad);
		elad_reserch = (Button) findViewById(R.id.elad_reserch);
		
		elad_adad.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_AD.this, ELAD_AD_List.class);
				startActivity(intent);
			}
		});
		
		elad_reserch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_AD.this, ELAD_RS_List.class);
				startActivity(intent);
			}
		});
	}
}
