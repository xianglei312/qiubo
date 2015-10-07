package com.R;

import org.yuner.www.CloseAll;
import org.yuner.www.ConnectedApp;
import org.yuner.www.bean.UserInfo;
import org.yuner.www.chatServices.InitData;
import org.yuner.www.commons.GlobalMsgTypes;
import org.yuner.www.commons.GlobalStrings;
import org.yuner.www.mainBody.MainBodyActivity;
import org.yuner.www.myNetwork.NetworkService;

import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import futureChange.BaseApplication;
import futureChange.ContentValue;

public class LoginActivity extends Activity implements OnClickListener {
	private String mName;
	private UserInfo mUserInfo;
	private String mPassword;
	private EditText mEtAccount;
	private EditText mEtPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		findViewById(R.id.mainLoginBtn).setOnClickListener(this);

		mEtAccount = (EditText) findViewById(R.id.mainLoginEditAccount);
		mEtPassword = (EditText) findViewById(R.id.mainLoginEditPassword);

		/* this is to render the password edittext font to be default */
		mEtPassword.setTypeface(Typeface.DEFAULT);
		mEtPassword.setTransformationMethod(new PasswordTransformationMethod());

	}

	public void tryLogin() {
		mName = mEtAccount.getText().toString();
		mPassword = mEtPassword.getText().toString();

		if (mName.equals("") || mPassword.length() < 5) {// Please Specify Your
															// Name and Sex"
			Toast.makeText(LoginActivity.this, R.string.please_set_name_and_age_correctly, Toast.LENGTH_LONG).show();
		} else {
			mUserInfo = new UserInfo(mName, 0, 0, 0, 0, 0, 0);

			/* if mNetcon is connected already, close it first */
			/*
			 * here we use try because mNetCon might not have been instantiated
			 * yet
			 */
			/*
			 * try { NetConnect.getnetConnect().closeNetConnect(); } catch
			 * (Exception e) {} try { InitData.closeInitData();
			 * FriendListInfo.closeFriendListInfo();
			 * ChatServiceData.closeChatServiceData(); } catch (Exception e) {}
			 */
			CloseAll.closeAll();
			/* to establish a new connect */

			NetworkService.getInstance().onInit(this);
			NetworkService.getInstance().setupConnection();
			if (NetworkService.getInstance().getIsConnected()) {
				String usrInfo = mUserInfo.toString() + GlobalStrings.signinDivider + mPassword
						+ GlobalStrings.signinDivider;
				NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgHandShake, usrInfo);
			} else {
				NetworkService.getInstance().closeConnection();
				Toast.makeText(this, R.string.failed_to_connect_toServer, Toast.LENGTH_LONG).show();
				return;
			}

			InitData initData = InitData.getInitData();
			initData.start();
			try {
				initData.join();
			} catch (Exception e) {
			}
			mUserInfo = initData.getUserInfo();

			if (mUserInfo.getId() < 0) {
				Toast.makeText(this, R.string.invalid_username_or_password, Toast.LENGTH_SHORT).show();
				return;
			}

			ConnectedApp connected_app0 = ConnectedApp.getInstance();

			connected_app0.setUserInfo(mUserInfo);
			connected_app0.clearListActivity();
			connected_app0.instantiateListActivity();

			// 登录成功后修改登录状态，存储用户信息
			BaseApplication.getApplication().saveUserInfo(mUserInfo);
			BaseApplication.getApplication().setConfingInfo(ContentValue.USER_PASSWORD, mPassword);

			Intent intent0 = new Intent(LoginActivity.this, MainBodyActivity.class);

			startActivity(intent0);

			finish();

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mainLoginBtn:

			tryLogin();
			break;

		default:
			break;
		}

	}

}
