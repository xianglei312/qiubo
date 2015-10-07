package com.eltong.elad;

import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ELAD_Re extends Activity {
	private Button elad_read = null;
	private Button elad_rereserch = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_re);
		
		elad_read = (Button) findViewById(R.id.elad_read);
		elad_rereserch = (Button) findViewById(R.id.elad_rereserch);
		
		elad_read.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Re.this, ELAD_read_List.class);
				startActivity(intent);
			}
		});
		
		elad_rereserch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ELAD_Re.this, ELAD_rereserch_List.class);
				startActivity(intent);
			}
		});
	}
}