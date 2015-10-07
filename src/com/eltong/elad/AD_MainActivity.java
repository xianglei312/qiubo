package com.eltong.elad;

import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AD_MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ad__main);
		
		findViewById(R.id.Ad_type_fuzhuang).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_tongxun).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_fangdichan).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_qiche).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_yingshi).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_meishi).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_hunqing).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_jiadian).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_lvyou).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_meirong).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_xiaoshangpin).setOnClickListener((OnClickListener) this);
		findViewById(R.id.Ad_type_yiliao).setOnClickListener((OnClickListener) this);
		
		findViewById(R.id.ad_main_jifen).setOnClickListener((OnClickListener) this);
		findViewById(R.id.ad_main_youhuijuan).setOnClickListener((OnClickListener) this);
		findViewById(R.id.ad_main_shangpinjilu).setOnClickListener((OnClickListener) this);
		findViewById(R.id.ad_main_guankanjilu).setOnClickListener((OnClickListener) this);
		findViewById(R.id.ad_main_diaoyanguankanjilu).setOnClickListener((OnClickListener) this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Ad_type_fuzhuang:
			BasicProperties.Ad_type = 1;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_tongxun:
			BasicProperties.Ad_type = 2;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_fangdichan:
			BasicProperties.Ad_type = 3;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_qiche:
			BasicProperties.Ad_type = 4;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_yingshi:
			BasicProperties.Ad_type = 5;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_meishi:
			BasicProperties.Ad_type = 6;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_hunqing:
			BasicProperties.Ad_type = 7;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_jiadian:
			BasicProperties.Ad_type = 8;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_lvyou:
			BasicProperties.Ad_type = 9;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_meirong:
			BasicProperties.Ad_type = 10;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_xiaoshangpin:
			BasicProperties.Ad_type = 11;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
		case R.id.Ad_type_yiliao:
			BasicProperties.Ad_type = 12;
			startActivity(new Intent(this, ELAD_AD_List.class));
			break;
			
		case R.id.ad_main_jifen:
			startActivity(new Intent(this, ELAD_point.class));
			break;
		case R.id.ad_main_youhuijuan:
			startActivity(new Intent(this, ELAD_coupon.class));
			break;
		case R.id.ad_main_shangpinjilu:
			startActivity(new Intent(this, ELAD_buy.class));
			break;
		case R.id.ad_main_guankanjilu:
			startActivity(new Intent(this, ELAD_read_List.class));
			break;
		case R.id.ad_main_diaoyanguankanjilu:
			startActivity(new Intent(this, ELAD_rereserch_List.class));
			break;
		default:		
			break;		
		}

	}
	
}
