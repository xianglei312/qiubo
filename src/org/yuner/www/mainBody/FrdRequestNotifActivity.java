package org.yuner.www.mainBody;

import java.util.ArrayList;

import org.yuner.www.ConnectedApp;
import org.yuner.www.bean.FrdReqNotifItemEntity;

import com.RTalk.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

public class FrdRequestNotifActivity extends Activity {

	private static FrdRequestNotifActivity mInstance;
	private static ArrayList<FrdReqNotifItemEntity> mListOfNotif;

	public static FrdRequestNotifActivity getInstance() {
		return mInstance;
	}

	public static ArrayList<FrdReqNotifItemEntity> getListOfNotif() {
		if (mListOfNotif == null) {
			mListOfNotif = new ArrayList<FrdReqNotifItemEntity>();
		}
		return mListOfNotif;
	}

	public static void clearListOfNotif() {
		mListOfNotif = null;
	}

	public static void newNotification(int type, int id, int imgId, String name, String content, String time,
			String strOfUser) {
		FrdReqNotifItemEntity entity = new FrdReqNotifItemEntity(type, id, imgId, name, content, time, strOfUser);

		if (mListOfNotif == null) {
			mListOfNotif = new ArrayList<FrdReqNotifItemEntity>();
		}
		mListOfNotif.add(entity);
	}

	private boolean mIsCurPage = false;
	private ListView mListView0;
	private FrdReqNotifItemEntity mEntity;
	private Dialog mDialog0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.tabmsg_frd_req_notif_page);

		mInstance = this;
		if (mListOfNotif == null) {
			mListOfNotif = new ArrayList<FrdReqNotifItemEntity>();
		}

		mListView0 = (ListView) findViewById(R.id.tabmsg_frd_req_notif_page_listview);

		mListView0.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		mIsCurPage = true;
		onUpdateView();

		try {
			FrdRequestNotifActivity preced = ConnectedApp.getInstance().getFrdRequestNotifActivity();
			preced.finish();
		} catch (Exception e) {
		}

		ConnectedApp.getInstance().setFrdRequestNotifActivity(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		mIsCurPage = false;
	}

	public void onPopupForResponse2(int position) {
		Intent intent0 = new Intent(FrdRequestNotifActivity.this, TabMsgFrdReqProcActivity.class);
		mEntity = mListOfNotif.get(position);

		if (mEntity.getType() == FrdReqNotifItemEntity.mTypeFrdReq
				&& mEntity.getStatus() == FrdReqNotifItemEntity.mUnanswer) {
			intent0.putExtra("itemStr", mEntity.toString());
			intent0.putExtra("itemPos", position);
			startActivity(intent0.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			overridePendingTransition(R.anim.my_slide_right_in, R.anim.my_slide_left_out);
		}
	}

	public void setItemStatus(int pos, int status) {
		mEntity = mListOfNotif.get(pos);
		mEntity.setStatus(status);
	}

	public boolean getIsCurPage() {
		return mIsCurPage;
	}

	public void onUpdateView() {
		mListView0.setAdapter(new FrdRequestNotifAdapter(this, mListOfNotif));
	}

}
