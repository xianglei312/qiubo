package com.eltong.elad;

import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ELAD_Qa extends Activity {
	
	private Button elad_q = null;
	private Button elad_a = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_qa);
		
		elad_q = (Button) findViewById(R.id.elad_q);
		elad_a = (Button) findViewById(R.id.elad_a);
		
		elad_q.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Qa.this, ELAD_qiatan_List.class);
				startActivity(intent);
			}
		});
		
		elad_a.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Qa.this, ELAD_zixun_List.class);
				startActivity(intent);
			}
		});
	}
}