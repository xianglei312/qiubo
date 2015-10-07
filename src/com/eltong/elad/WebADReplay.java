package com.eltong.elad;

import com.RTalk.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebADReplay extends Activity {

	private WebView wv_ad = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_webadreplay);
		
		String html = BasicProperties.html;
		wv_ad = (WebView) findViewById(R.id.ponittab_webadreplay);
		wv_ad.getSettings().setSupportZoom(true);
		wv_ad.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		wv_ad.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				if (newProgress == 100) {
					handler.sendEmptyMessage(1);
				}
				super.onProgressChanged(view, newProgress);
			}

		});
		wv_ad.loadData(html, "text/html; charset=UTF-8",null);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				//wait_dialog.dismiss();
				break;
			}
		}
	};
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
//			Intent intent = new Intent();
//			intent.setClass(WebADReplay.this,
//					ADHistoryDetailed.class);
//			startActivity(intent);
			WebADReplay.this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
