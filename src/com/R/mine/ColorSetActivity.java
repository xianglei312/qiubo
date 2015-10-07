package com.R.mine;

import org.yuner.www.chatter.ChatActivity;
import org.yuner.www.commons.MyBackground;

import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ColorSetActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.colorsettings);

		findViewById(R.id.btn_1).setOnClickListener(this);
		findViewById(R.id.btn_2).setOnClickListener(this);
		findViewById(R.id.btn_3).setOnClickListener(this);
		findViewById(R.id.btn_4).setOnClickListener(this);
		findViewById(R.id.btn_5).setOnClickListener(this);
		findViewById(R.id.btn_6).setOnClickListener(this);
		findViewById(R.id.btn_7).setOnClickListener(this);
		findViewById(R.id.btn_8).setOnClickListener(this);
		findViewById(R.id.btn_9).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_1:

			MyBackground.MYBACKGROUND = 1;
			startActivity(new Intent(this, ChatActivity.class));
			break;
		case R.id.btn_2:

			MyBackground.MYBACKGROUND = 2;
			startActivity(new Intent(this, ChatActivity.class));
			break;
		case R.id.btn_3:
			MyBackground.MYBACKGROUND = 3;
			startActivity(new Intent(this, ChatActivity.class));
			break;
		case R.id.btn_4:
			MyBackground.MYBACKGROUND = 4;
			startActivity(new Intent(this, ChatActivity.class));
			break;
		case R.id.btn_5:
			MyBackground.MYBACKGROUND = 5;
			startActivity(new Intent(this, ChatActivity.class));
			break;
		case R.id.btn_6:
			MyBackground.MYBACKGROUND = 6;
			startActivity(new Intent(this, ChatActivity.class));
			break;
		case R.id.btn_7:
			MyBackground.MYBACKGROUND = 7;
			startActivity(new Intent(this, ChatActivity.class));
			break;
		case R.id.btn_8:
			MyBackground.MYBACKGROUND = 8;
			startActivity(new Intent(this, ChatActivity.class));
			break;
		case R.id.btn_9:
			MyBackground.MYBACKGROUND = 9;
			startActivity(new Intent(this, ChatActivity.class));
			break;

		default:
			break;
		}
		finish();
	}

}
