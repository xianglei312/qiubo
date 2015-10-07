package com.eltong.elad;

import com.RTalk.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ConsultDatailed extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.elad_servicetab_consult_datailed);
		
		Bundle bundle = getIntent().getExtras();
		
		TextView servicetab_consult_textviewa = (TextView) findViewById(R.id.servicetab_consult_textviewa);
		servicetab_consult_textviewa.setText(bundle.getString("textviewa"));
		TextView servicetab_consult_textviewb = (TextView) findViewById(R.id.servicetab_consult_textviewb);
		servicetab_consult_textviewb.setText(bundle.getString("textviewb"));
		TextView servicetab_consult_textviewc = (TextView) findViewById(R.id.servicetab_consult_textviewc);
		servicetab_consult_textviewc.setText(bundle.getString("textviewc"));
		TextView servicetab_consult_textviewd = (TextView) findViewById(R.id.servicetab_consult_textviewd);
		servicetab_consult_textviewd.setText(bundle.getString("textviewd"));
		TextView servicetab_consult_textviewe = (TextView) findViewById(R.id.servicetab_consult_textviewe);
		servicetab_consult_textviewe.setText(bundle.getString("textviewe"));
		TextView servicetab_consult_textviewf = (TextView) findViewById(R.id.servicetab_consult_textviewf);
		servicetab_consult_textviewf.setText(bundle.getString("textviewf"));
		TextView servicetab_consult_textviewg = (TextView) findViewById(R.id.servicetab_consult_textviewg);
		servicetab_consult_textviewg.setText(bundle.getString("textviewg"));
	
	}
}
