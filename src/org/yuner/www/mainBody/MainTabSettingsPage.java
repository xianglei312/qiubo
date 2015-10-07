/**
 * 
 * This is more page
 * 
 */

package org.yuner.www.mainBody;

import com.R.mine.MineSettingsActivity;
import com.RTalk.R;
import com.elt.elsms.BasicPropertiess;
import com.elt.elsms.ELSMSActivity;
import com.elt.elsms.RtalkGongGaoActivity;
import com.eltong.elad.AD_MainActivity;
import com.eltong.elad.BasicProperties;
import com.eltong.elad.ELAD_Main;
import com.eltong.elad.ELAD_RS_List;
import com.eltong.elad.ELAD_coupon;
import com.mytranslate.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainTabSettingsPage implements OnClickListener {

	private static MainTabSettingsPage mInstance;

	private View mViewOfPage;
	private Context mContext0;

	public static MainTabSettingsPage getInstance() {
		if (mInstance == null) {
			mInstance = new MainTabSettingsPage();
		}
		return mInstance;
	}

	private MainTabSettingsPage() {
	}

	public void onInit(View view, Context context) {
		mViewOfPage = view;
		mContext0 = context;
	}

	public void onCreate() {

		mViewOfPage.findViewById(R.id.imgMine).setOnClickListener(this);
		mViewOfPage.findViewById(R.id.myAdvertisement).setOnClickListener(this);
		mViewOfPage.findViewById(R.id.mySms).setOnClickListener(this);
		mViewOfPage.findViewById(R.id.rtalk_gonggao).setOnClickListener(this);
		mViewOfPage.findViewById(R.id.rtalk_diaoyan).setOnClickListener(this);
		mViewOfPage.findViewById(R.id.rtalk_youhuijuan).setOnClickListener(this);
		mViewOfPage.findViewById(R.id.layoutTranslate).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgMine:

			mContext0.startActivity(new Intent(mContext0, MineSettingsActivity.class));

			break;

		case R.id.myAdvertisement:
			BasicProperties.MEMBER_ID = 206;
			BasicProperties.Telephone = "8613664229856";
			BasicProperties.Name = "xxd";
			mContext0.startActivity(new Intent(mContext0, AD_MainActivity.class));
			break;

		case R.id.mySms:
			BasicPropertiess.telephone = "8613664229856";

			mContext0.startActivity(new Intent(mContext0, ELSMSActivity.class));

			break;

		case R.id.rtalk_gonggao:
			BasicPropertiess.telephone = "8613664229856";

			mContext0.startActivity(new Intent(mContext0, RtalkGongGaoActivity.class));

			break;
		case R.id.rtalk_diaoyan:
			BasicProperties.MEMBER_ID = 206;
			BasicProperties.Telephone = "8613664229856";
			BasicProperties.Name = "xxd";
			mContext0.startActivity(new Intent(mContext0, ELAD_RS_List.class));
			break;

		case R.id.rtalk_youhuijuan:
			BasicProperties.MEMBER_ID = 206;
			BasicProperties.Telephone = "8613664229856";
			BasicProperties.Name = "xxd";
			mContext0.startActivity(new Intent(mContext0, ELAD_coupon.class));

			break;
		case R.id.layoutTranslate:

			mContext0.startActivity(new Intent(mContext0, MainActivity.class));

			break;

		default:
			break;
		}

	}
}
