package com.eltong.elad;

import com.RTalk.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PiontDetailed extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_tabsegmentpiont_detailed);

		Bundle bundle = getIntent().getExtras();
		
		TextView pointtab_note_textviewa = (TextView) findViewById(R.id.pointtab_note_textviewa);
		pointtab_note_textviewa.setText(bundle.getString("textviewa"));

		TextView pointtab_note_textviewb = (TextView) findViewById(R.id.pointtab_note_textviewb);
		pointtab_note_textviewb.setText(bundle.getString("textviewb"));

		TextView pointtab_note_textviewc = (TextView) findViewById(R.id.pointtab_note_textviewc);
		pointtab_note_textviewc.setText(bundle.getString("textviewc"));

		TextView pointtab_note_textviewl = (TextView) findViewById(R.id.pointtab_note_textviewl);
		pointtab_note_textviewl.setText(bundle.getString("textviewl"));
		
		TextView pointtab_note_textviewd = (TextView) findViewById(R.id.pointtab_note_textviewd);
		pointtab_note_textviewd.setText(bundle.getString("textviewd"));

		TextView pointtab_note_textviewe = (TextView) findViewById(R.id.pointtab_note_textviewe);
		pointtab_note_textviewe.setText(bundle.getString("textviewe"));

		TextView pointtab_note_textviewf = (TextView) findViewById(R.id.pointtab_note_textviewf);
		pointtab_note_textviewf.setText(bundle.getString("textviewf"));

		TextView pointtab_note_textviewg = (TextView) findViewById(R.id.pointtab_note_textviewg);
		pointtab_note_textviewg.setText(bundle.getString("textviewg"));

		TextView pointtab_note_textviewh = (TextView) findViewById(R.id.pointtab_note_textviewh);
		pointtab_note_textviewh.setText(bundle.getString("textviewh"));

		TextView pointtab_note_textviewi = (TextView) findViewById(R.id.pointtab_note_textviewi);
		pointtab_note_textviewi.setText(bundle.getString("textviewi"));

		TextView pointtab_note_textviewj = (TextView) findViewById(R.id.pointtab_note_textviewj);
		pointtab_note_textviewj.setText(bundle.getString("textviewj"));
		
	}

}
