package org.yuner.www;

import com.R.LoginActivity;
import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_login);

		Intent serviceIntent = new Intent("HeartbeatService");
		serviceIntent.putExtra("url", "115.68.13.59:8080");
		startService(serviceIntent);
		findViewById(R.id.BtnLogin).setOnClickListener(this);
		findViewById(R.id.main_btn_register).setOnClickListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.BtnLogin:
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
			finish();
			break;
		case R.id.main_btn_register:
			Intent intent0 = new Intent(MainActivity.this, RegisterActivity.class);
			startActivity(intent0);
			finish();
			break;

		default:
			break;
		}

	}

}