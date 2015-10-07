package com.eltong.elad;

import com.RTalk.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

public class CouponDetailed extends Activity {
	
	private String imgurl = "";
	ImageView servicetab_coupon_detailed_pic = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_coupon_detailed);

		Bundle bundle = getIntent().getExtras();
		
		imgurl = bundle.getString("textviewb");
		
		TextView servicetab_coupon_detailed_title = (TextView) findViewById(R.id.servicetab_coupon_detailed_title);
		servicetab_coupon_detailed_title.setText(bundle.getString("textviewa"));
		servicetab_coupon_detailed_pic = (ImageView) findViewById(R.id.servicetab_coupon_detailed_pic);

		TextView servicetab_coupon_detailed_content = (TextView) findViewById(R.id.servicetab_coupon_detailed_content);
		servicetab_coupon_detailed_content.setText(bundle.getString("textviewc"));

		new Thread() {
			public void run() {			
				try {
					byte[] data = NetTool.getImage(imgurl);
					Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
					Message msg = new Message();
					msg.what = 0;
					msg.obj = bm;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception

				}
			}
		}.start();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case 0:
				Bitmap bm = (Bitmap) msg.obj;
				servicetab_coupon_detailed_pic.setImageBitmap(bm);
				break;

			}
		};
	};

}
