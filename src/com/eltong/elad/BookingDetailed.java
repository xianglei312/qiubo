package com.eltong.elad;

import com.RTalk.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BookingDetailed extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.elad_serviceteb_booking_detailed);
		
		Bundle bundle = getIntent().getExtras();
		
		TextView servicetab_booking_textviewa = (TextView) findViewById(R.id.servicetab_booking_textviewa);
		servicetab_booking_textviewa.setText(bundle.getString("textviewa"));
		TextView servicetab_booking_textviewb = (TextView) findViewById(R.id.servicetab_booking_textviewb);
		servicetab_booking_textviewb.setText(bundle.getString("textviewb"));
		TextView servicetab_booking_textviewc = (TextView) findViewById(R.id.servicetab_booking_textviewc);
		servicetab_booking_textviewc.setText(bundle.getString("textviewc"));
		TextView servicetab_booking_textviewd = (TextView) findViewById(R.id.servicetab_booking_textviewd);
		servicetab_booking_textviewd.setText(bundle.getString("textviewd"));
		TextView servicetab_booking_textviewe = (TextView) findViewById(R.id.servicetab_booking_textviewe);
		servicetab_booking_textviewe.setText(bundle.getString("textviewe"));
		TextView servicetab_booking_textviewf = (TextView) findViewById(R.id.servicetab_booking_textviewf);
		servicetab_booking_textviewf.setText(bundle.getString("textviewf"));
		TextView servicetab_booking_textviewg = (TextView) findViewById(R.id.servicetab_booking_textviewg);
		servicetab_booking_textviewg.setText(bundle.getString("textviewg"));

	
	}
}
