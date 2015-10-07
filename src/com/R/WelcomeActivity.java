package com.R;

import org.yuner.www.CloseAll;
import org.yuner.www.ConnectedApp;
import org.yuner.www.MainActivity;
import org.yuner.www.bean.UserInfo;
import org.yuner.www.chatServices.InitData;
import org.yuner.www.commons.GlobalMsgTypes;
import org.yuner.www.commons.GlobalStrings;
import org.yuner.www.mainBody.MainBodyActivity;
import org.yuner.www.myNetwork.NetworkService;

import com.R.welcome.GuideActivity;
import com.RTalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import futureChange.BaseApplication;
import futureChange.ContentValue;

public class WelcomeActivity extends Activity {

	private ImageView welcome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		Log.d("rtalk", "welcomeActivity.oncreate");
		setContentView(R.layout.activity_welcome);
		Log.d("rtalk", "isLogin:" + BaseApplication.getApplication().isLogin());
		
		welcome = (ImageView) findViewById(R.id.welcome);
		welcome.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if (!BaseApplication.getApplication().ishideWelcomePage()) {
					BaseApplication.getApplication().setConfingInfo(ContentValue.HIDE_WELCOME_PAGE, true);
					Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
					startActivity(intent);
					finish();
				} else {
					if (BaseApplication.getApplication().isLogin()) {
						// 自动登录
						tryLogin();
					} else {
						Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
		}, 2000);
	}

	public void tryLogin() {
		String name = BaseApplication.getApplication().getConfigInfo(ContentValue.USER_NAME);
		String password = BaseApplication.getApplication().getConfigInfo(ContentValue.USER_PASSWORD);

		if (name.equals("") || password.length() < 5) {// Please Specify Your
														// Name and Sex"
			Toast.makeText(getApplicationContext(), R.string.please_set_name_and_age_correctly, Toast.LENGTH_LONG)
					.show();
		} else {
			UserInfo mUserInfo = new UserInfo(name, 0, 0, 0, 0, 0, 0);

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
				String usrInfo = mUserInfo.toString() + GlobalStrings.signinDivider + password
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

			//登录成功后修改登录状态，存储用户信息
			BaseApplication.getApplication().saveUserInfo(mUserInfo);
			BaseApplication.getApplication().setConfingInfo(ContentValue.USER_PASSWORD, password);
			
			Intent intent0 = new Intent(WelcomeActivity.this, MainBodyActivity.class);
			startActivity(intent0);

			finish();

		}
	}

}
