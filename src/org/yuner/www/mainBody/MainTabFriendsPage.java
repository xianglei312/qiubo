/**
 * 
 * This is circle_of_friends page
 * 
 */

package org.yuner.www.mainBody;

import com.R.mine.MineSettingsActivity;
import com.RTalk.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainTabFriendsPage implements OnClickListener {

	private static MainTabFriendsPage mInstance;

	private View mViewOfPage;
	private Context mContext0;

	public static MainTabFriendsPage getInstance() {
		if (mInstance == null) {
			mInstance = new MainTabFriendsPage();
		}
		return mInstance;
	}

	private MainTabFriendsPage() {
	}

	public void onInit(View view, Context cont) {
		mViewOfPage = view;
		mContext0 = cont;
	}

	public void onCreate() {

		mViewOfPage.findViewById(R.id.imgFriendMine).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		mContext0.startActivity(new Intent(mContext0, MineSettingsActivity.class));

	}
}
