package org.yuner.www;

import org.yuner.www.bean.UserInfo;
import org.yuner.www.chatServices.InitData;
import org.yuner.www.commons.GlobalErrors;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private static RegisterActivity mInstance;

	private EditText mEtNickname;
	private EditText mEtPassword;
	private EditText mEtConfirmPassword;
	private Spinner mSpBirthYear;
	private Spinner mSpBirthMonth;
	private Spinner mSpBirthDay;
	private RadioGroup mRgpSex;
	private UserInfo mUserInfo;
	private Button mBtnRegister;
	String name, password;
	private UserInfo mUserAfterRegister;
	private boolean mReceivedRegister;

	public static RegisterActivity getInstance() {
		return mInstance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_register);

		mInstance = this;

		mEtNickname = (EditText) findViewById(R.id.main_register_edittext_nickname);
		mEtPassword = (EditText) findViewById(R.id.main_register_edittext_password);
		mEtConfirmPassword = (EditText) findViewById(R.id.main_register_edittext_confirm_password);
		mSpBirthYear = (Spinner) findViewById(R.id.main_register_spinner_year);
		mSpBirthMonth = (Spinner) findViewById(R.id.main_register_spinner_month);
		mSpBirthDay = (Spinner) findViewById(R.id.main_register_spinner_day);
		mRgpSex = (RadioGroup) findViewById(R.id.mainRegisterRgpChooseSex);

		/* this is to render the password edittext font to be default */
		mEtPassword.setTypeface(Typeface.DEFAULT);
		mEtPassword.setTransformationMethod(new PasswordTransformationMethod());
		mEtConfirmPassword.setTypeface(Typeface.DEFAULT);
		mEtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());

		mBtnRegister = (Button) findViewById(R.id.main_register_btn_register);

		mBtnRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				name = mEtNickname.getText().toString().trim();
				password = mEtPassword.getText().toString().trim();
				String confirmPassword = mEtConfirmPassword.getText().toString().trim();

				int birthYear = mSpBirthYear.getSelectedItemPosition() + 1941;
				int birthMonth = mSpBirthMonth.getSelectedItemPosition() + 1;
				int birthDay = mSpBirthDay.getSelectedItemPosition() + 1;

				int sex;
				int sexChoseId = mRgpSex.getCheckedRadioButtonId();
				switch (sexChoseId) {
				case R.id.mainRegisterRdBtnFemale:
					sex = 0;
					break;
				case R.id.mainRegisterRdBtnMale:
					sex = 1;
					break;
				default:
					sex = -1;
					break;
				}

				if (name.equals("")) {
					Toast.makeText(RegisterActivity.this, R.string.please_set_name_and_age_correctly,
							Toast.LENGTH_SHORT).show();
					return;
				} else if (password.length() < 5) {
					Toast.makeText(RegisterActivity.this, R.string.password_should_more_than_5, Toast.LENGTH_SHORT)
							.show();
					return;
				} else if (!password.equals(confirmPassword)) {
					Toast.makeText(RegisterActivity.this, R.string.confirmed_password_same, Toast.LENGTH_SHORT).show();
					return;
				} else if (sex < 0) {
					Toast.makeText(RegisterActivity.this, R.string.please_choose_gender, Toast.LENGTH_SHORT).show();
					return;
				}

				tryRegister(name, password, birthYear, birthMonth, birthDay, sex);
			}

		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mInstance = null;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish(); // destroy RegisterActivity when leave
	}

	public void tryRegister(String name, String password, int birthYear, int birthMonth, int birthDay, int sex) {
		UserInfo uu0 = new UserInfo(name, 0, sex, birthYear, birthMonth, birthDay, 0);

		String toRegister = uu0.toString() + GlobalStrings.signupDivider + password + GlobalStrings.signupDivider;

		NetworkService.getInstance().closeConnection();
		NetworkService.getInstance().onInit(this);
		NetworkService.getInstance().setupConnection();
		if (NetworkService.getInstance().getIsConnected()) {
			NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgSignUp, toRegister);
		} else {
			NetworkService.getInstance().closeConnection();
			Toast.makeText(this, R.string.failed_to_connect_toServer, Toast.LENGTH_LONG).show();
			return;
		}

		mReceivedRegister = false;
		while (!mReceivedRegister) {
		}

		if (mUserAfterRegister.getId() == GlobalErrors.nameAlreadyExists) {
			Toast.makeText(this, R.string.name_exist, Toast.LENGTH_SHORT).show();
		} else if (mUserAfterRegister.getId() >= 0) {
			Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show();
			tryLogin();

		} else {
			Toast.makeText(this, R.string.sorry_cannot_register, Toast.LENGTH_SHORT).show();
		}

		// con.closeNetConnect();
	}

	public void uponRegister(String msg0) {
		mUserAfterRegister = new UserInfo(msg0);
		mReceivedRegister = true;
	}

	public void tryLogin() {

		if (name.equals("") || password.length() < 5) {// Please Specify Your
														// Name and Sex"
			Toast.makeText(RegisterActivity.this, R.string.please_set_name_and_age_correctly, Toast.LENGTH_LONG).show();
		} else {
			mUserInfo = new UserInfo(name, 0, 0, 0, 0, 0, 0);

			CloseAll.closeAll();

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

			Intent intent0 = new Intent(RegisterActivity.this, MainBodyActivity.class);

			startActivity(intent0);

			finish();
		}
	}

}