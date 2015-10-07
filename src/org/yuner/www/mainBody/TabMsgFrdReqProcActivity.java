package org.yuner.www.mainBody;

import org.yuner.www.ConnectedApp;
import org.yuner.www.bean.FrdReqNotifItemEntity;
import org.yuner.www.bean.FrdRequestEntity;
import org.yuner.www.bean.UserInfo;
import org.yuner.www.chatServices.FriendListInfo;
import org.yuner.www.commons.GlobalMsgTypes;
import org.yuner.www.myNetwork.NetworkService;

import com.RTalk.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TabMsgFrdReqProcActivity extends Activity {

	private FrdReqNotifItemEntity mThisItem;

	private TextView mTvHeadName;
	private ImageView mImgIcon;
	private TextView mTvGender;
	private TextView mTvAge;
	private Button mBtnAccept;
	private Button mBtnDecline;

	private int mThisPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.tabmsg_frd_req_proc2);

		Intent intent0 = getIntent();
		String in = intent0.getStringExtra("itemStr");
		mThisPos = intent0.getIntExtra("itemPos", 0);
		mThisItem = new FrdReqNotifItemEntity(in);

		mTvHeadName = (TextView) findViewById(R.id.tabmsg_frd_req_proc_header_name);
		mImgIcon = (ImageView) findViewById(R.id.tabmsg_frd_req_proc_icon);
		mTvGender = (TextView) findViewById(R.id.tabmsg_frd_req_proc_gender);
		mTvAge = (TextView) findViewById(R.id.tabmsg_frd_req_proc_age);
		mBtnAccept = (Button) findViewById(R.id.tabmsg_frd_req_proc_btn_accept);
		mBtnDecline = (Button) findViewById(R.id.tabmsg_frd_req_proc_btn_decline);

		mTvHeadName.setText(mThisItem.getName());
		if (mThisItem.getImgId() == 0) {
			mImgIcon.setImageResource(R.drawable.cb0_h001);
		} else {
			mImgIcon.setImageResource(R.drawable.cb0_h003);
		}

		UserInfo uu0 = new UserInfo(mThisItem.getStrOfUser());
		if (uu0.getSex() == UserInfo.girl) {
			mTvGender.setText("Girl");
		} else {
			mTvGender.setText("Guy");
		}

		mTvAge.setText(uu0.getAge() + "");

		mBtnAccept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TabMsgFrdReqProcActivity.this);

				// set title
				alertDialogBuilder.setTitle(null);
				// set dialog message
				alertDialogBuilder.setMessage(R.string.sure_accent).setCancelable(true)
						.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						FrdRequestEntity reqEnt0 = new FrdRequestEntity(new UserInfo(mThisItem.getStrOfUser()),
								ConnectedApp.getInstance().getUserInfo());
						reqEnt0.accept();
						NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgFriendshipRequestResponse,
								reqEnt0.toString());

						mThisItem.setStatus(FrdReqNotifItemEntity.mAccepted);
						FrdRequestNotifActivity.getInstance().setItemStatus(mThisPos, FrdReqNotifItemEntity.mAccepted);

						FriendListInfo.getFriendListInfo().uponMakeNewFriend(new UserInfo(mThisItem.getStrOfUser()));
						startActivity(new Intent(TabMsgFrdReqProcActivity.this, MainBodyActivity.class));
					}

				}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						startActivity(new Intent(TabMsgFrdReqProcActivity.this, MainBodyActivity.class));
					}
				});
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
			}
		});

		mBtnDecline.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TabMsgFrdReqProcActivity.this);
				// set title
				alertDialogBuilder.setTitle(null);
				// set dialog message
				alertDialogBuilder.setMessage(R.string.sure_decline).setCancelable(true)
						.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						FrdRequestEntity reqEnt0 = new FrdRequestEntity(new UserInfo(mThisItem.getStrOfUser()),
								ConnectedApp.getInstance().getUserInfo());
						reqEnt0.decline();
						NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgFriendshipRequestResponse,
								reqEnt0.toString());

						mThisItem.setStatus(FrdReqNotifItemEntity.mDeclined);
						FrdRequestNotifActivity.getInstance().setItemStatus(mThisPos, FrdReqNotifItemEntity.mDeclined);
						startActivity(new Intent(TabMsgFrdReqProcActivity.this, MainBodyActivity.class));
					}
				}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						startActivity(new Intent(TabMsgFrdReqProcActivity.this, MainBodyActivity.class));
					}
				});
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
			}
		});
	}

}
