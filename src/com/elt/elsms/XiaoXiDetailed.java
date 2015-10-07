package com.elt.elsms;

import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class XiaoXiDetailed extends Activity {

	private TextView xiaoxidetailedpage_content =null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiaoxidetailedpage);
		
		xiaoxidetailedpage_content = (TextView)findViewById(R.id.xiaoxidetailedpage_content);
		
		Intent intent=getIntent();
		String neirong=intent.getStringExtra("neirong");
		
		xiaoxidetailedpage_content.setText(neirong);
		
	}
}
