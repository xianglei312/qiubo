/**
 * 
 * This is firendlist page
 * 
 * 
 */

package org.yuner.www.mainBody;

import com.R.mine.MineSettingsActivity;
import com.RTalk.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ChooseRoomPage {

	private static ChooseRoomPage mInstance;
	private ImageButton mImgButton;
	private View mViewOfPage;
	private ListView mListview;
	private Context mContext0;
	private RelativeLayout mLayoutByName;
	private RelativeLayout mLayoutByElse;

	public static ChooseRoomPage getInstance() {
		if (mInstance == null) {
			mInstance = new ChooseRoomPage();
		}
		return mInstance;
	}

	private ChooseRoomPage() {
	}

	public void onInit(View viewOfPage, Context context0) {
		mViewOfPage = viewOfPage;
		mContext0 = context0;
	}

	public void onCreate() {
		mImgButton = (ImageButton) mViewOfPage.findViewById(R.id.imgRoomMine);
		mImgButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mContext0.startActivity(new Intent(mContext0, MineSettingsActivity.class));

			}
		});

		mListview = (ListView) mViewOfPage.findViewById(R.id.cb0ChooseRoomList);
		mListview.setAdapter(new ChooseRoomAdapter(mContext0));
		mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					break;
				case 1:
					MainBodyActivity.getInstance().gotoPage(MainBodyActivity.mTabNumContacts,
							MainBodyActivity.mPageFriendList);
				default:
					break;
				}
			}
		});

		mLayoutByName = (RelativeLayout) mViewOfPage.findViewById(R.id.main_tab_friends_find_by_name);
		mLayoutByElse = (RelativeLayout) mViewOfPage.findViewById(R.id.main_tab_friends_find_by_else);

		mLayoutByName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainBodyActivity.getInstance().gotoSearchBynameActivity();
			}
		});

		mLayoutByElse.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainBodyActivity.getInstance().gotoSearchByelseActivity();
			}
		});
	}

}
