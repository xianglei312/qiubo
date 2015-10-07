package org.yuner.www.chatter;

import java.util.ArrayList;
import java.util.List;

import org.yuner.www.ConnectedApp;
import org.yuner.www.bean.ChatEntity;
import org.yuner.www.chatServices.ChatService;
import org.yuner.www.chatServices.ChatServiceData;
import org.yuner.www.commons.MyBackground;
import org.yuner.www.util.DbSaveOldMsg;

import com.RTalk.R;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity {

	private static boolean mIsActive = false;
	private LinearLayout ll;
	private Handler mHandler;

	/* views contained in this activity */
	public ListView mListviewHistory = null;
	private EditText mEtInput = null;
	private Button mBtnSend = null;
	private ImageView mImvReadymade = null;
	private ReadyMadeDialog mRmDialog;

	private ChatListviewAdapter mListviewAdapter;

	/* the top banner needs to maintain its size unchanged */
	private RelativeLayout mRlTop;
	private TextView mTvTop;

	/*
	 * records of current line amount and current height of the layout contains
	 * etDo
	 */
	private LinearLayout mLlEtDo;
	private int mHeightOfTextLayout = 0; // height of layout contains etDo in
											// pixel
	private int mCurLineAcc; // current line account

	/* PublicService and service connection defined here */
	private ChatService mPublicService;
	private ServiceConnection mServiceConnect = new ServiceConnection() {
		

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			mPublicService = ((ChatService.ChatBinder) binder).getService();
			mPublicService.setBoundChatActivity(ChatActivity.this);
			new ToDisplayHistory(mHandler).start();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

			
//			new ToDisplayHistory(mHandler).start();

		}
	};

	/* instantiate a TextWatcher to auto-tweak the height of input editText */
	private TextWatcher mTextWatcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {
			int LineAcc = ChatActivity.this.mEtInput.getLineCount();
			if (LineAcc > ChatActivity.this.mCurLineAcc && LineAcc <= 7) {
				// test codes
				int topHeight = mRlTop.getHeight();
				int lineIncre = LineAcc - ChatActivity.this.mCurLineAcc;
				ChatActivity.this.mHeightOfTextLayout += lineIncre * ChatActivity.this.mEtInput.getLineHeight();
				LinearLayout.LayoutParams imagebtn_params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				imagebtn_params.height = ChatActivity.this.mHeightOfTextLayout;
				ChatActivity.this.mLlEtDo.setLayoutParams(imagebtn_params);

				ChatActivity.this.mCurLineAcc = LineAcc;

				// test codes
				LinearLayout.LayoutParams topBanner_params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				topBanner_params.height = topHeight;
				ChatActivity.this.mRlTop.setLayoutParams(topBanner_params);
			}
		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {
			String edttext = mEtInput.getText().toString();;
			// TODO Auto-generated method stub
			if (edttext.equals("广告")||edttext.equals("광고")) {
				Log.v("log", s + "***********ok**********");
				ll.setVisibility(View.VISIBLE);
				
				mHandlera.postDelayed(mRunnable,3000);
				
			} else {
				ll.setVisibility(View.GONE);
				Log.v("log", s + "***********error**********");
			}
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			ChatActivity.this.mHeightOfTextLayout = ChatActivity.this.mLlEtDo.getHeight();
		}
	};
	
	private Runnable mRunnable = new Runnable() {
	@Override
	public void run() {
	mHandlera.sendEmptyMessage(1);
	}
	};

	Handler mHandlera = new Handler() {
	@Override
	public void handleMessage(Message msg) {
	super.handleMessage(msg);
	//3s后执行代码
	ll.setVisibility(View.GONE);
	}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.cb0_chat);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		ConnectedApp.getInstance().addItemIntoListActivity(this);

		mListviewHistory = (ListView) findViewById(R.id.cb0ChatListview);
		mEtInput = (EditText) findViewById(R.id.cb0ChatMsgInput);
		mBtnSend = (Button) findViewById(R.id.cb0ChatBtnSendMsg);
		mImvReadymade = (ImageView) findViewById(R.id.cb0ChatImvReadymade);
		mTvTop = (TextView) findViewById(R.id.cb0ChatFriendName);
		
		ll = (LinearLayout) findViewById(R.id.cb0ChatLayoutimage);
		ll.setVisibility(View.GONE);

		switch (MyBackground.MYBACKGROUND) {
		case 0:

			break;
		case 1:
			findViewById(R.id.rlChatbackground).setBackgroundResource(R.drawable.btn_1);
			break;
		case 2:
			findViewById(R.id.rlChatbackground).setBackgroundResource(R.drawable.btn_2);
			break;
		case 3:
			findViewById(R.id.rlChatbackground).setBackgroundResource(R.drawable.btn_3);
			break;
		case 4:
			findViewById(R.id.rlChatbackground).setBackgroundResource(R.drawable.btn_4);
			break;
		case 5:
			findViewById(R.id.rlChatbackground).setBackgroundResource(R.drawable.btn_5);
			break;
		case 6:
			findViewById(R.id.rlChatbackground).setBackgroundResource(R.drawable.btn_6);
			break;
		case 7:
			findViewById(R.id.rlChatbackground).setBackgroundResource(R.drawable.btn_7);
			break;
		case 8:
			findViewById(R.id.rlChatbackground).setBackgroundResource(R.drawable.btn_8);
			break;
		case 9:
			findViewById(R.id.rlChatbackground).setBackgroundResource(R.drawable.btn_sun);
			break;
		default:
			break;
		}

		// pop this activity into listActivity define in ConnectedApp
		ConnectedApp connected_app0 = ConnectedApp.getInstance();
		connected_app0.addItemIntoListActivity(this);

		mListviewHistory.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					if (mListviewHistory.getFirstVisiblePosition() == 0) {
						// Toast.makeText(ChatActivity.this, "sorry top
						// already", Toast.LENGTH_SHORT).show();
						DbSaveOldMsg.onInit(ChatActivity.this);

						int myId = ConnectedApp.getInstance().getUserInfo().getId();
						int id = ChatService.getInstance().getId();
						ArrayList<ChatEntity> mapFriendsEntity = (ArrayList<ChatEntity>) ChatServiceData.getInstance()
								.getCurMsg(2, id);
						ArrayList<Boolean> mapFriendsSelf = (ArrayList<Boolean>) ChatServiceData.getInstance()
								.getCurIsSelf(2, id);

						int incre = DbSaveOldMsg.getInstance().getMsg(mapFriendsEntity, mapFriendsSelf, myId, id);

						int selection = mListviewHistory.getSelectedItemPosition();

						mListviewAdapter.notifyDataSetChanged();
						mListviewHistory.setSelection(selection + incre);
					}
					break;
				default:
					break;
				}

				return false;
			}
		});
		

		/* add TextWatcher to watch for text line account changing */
		mLlEtDo = (LinearLayout) findViewById(R.id.cb0ChatLayoutMsg);
		mCurLineAcc = 1;
		mEtInput.addTextChangedListener(mTextWatcher);

		mRlTop = (RelativeLayout) findViewById(R.id.cb0ChatTopBanner);

		mImvReadymade.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// ChatEmoticons chatEmoticon = new
				// ChatEmoticons(ChatActivity.this, mEtInput);
				// chatEmoticon.createExpressionDialog();
				View view = getLayoutInflater().inflate(R.layout.kaifa, null);

				Toast viewToast = new Toast(ChatActivity.this);
				viewToast.setDuration(Toast.LENGTH_SHORT);
				viewToast.setView(view);
				viewToast.show();

			}

		});

		mBtnSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String st0 = mEtInput.getText().toString();

				mEtInput.setText("");

				if (!st0.equals("")) {
					// test codes
					int topHeight = mRlTop.getHeight();

					LinearLayout.LayoutParams imagebtn_params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					imagebtn_params.height = mLlEtDo.getHeight() - (mCurLineAcc - 1) * (mEtInput.getLineHeight());
					mLlEtDo.setLayoutParams(imagebtn_params);
					mCurLineAcc = 1;

					// test codes
					LinearLayout.LayoutParams topBanner_params = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					topBanner_params.height = topHeight;
					mRlTop.setLayoutParams(topBanner_params);
				}

				if (!st0.equals("")) {
					mPublicService.sendMyMessage(st0);
				}
			}
		});

		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					mPublicService.chatActivityDisplayHistory();
					break;
				}
				super.handleMessage(msg);
			}

		};
	}

	@Override
	protected void onResume() {
		super.onResume();
		ConnectedApp.getInstance().setCurActivity(this);
		ChatActivity.mIsActive = true;
		Intent intentTemp = new Intent(ChatActivity.this, ChatService.class);
		bindService(intentTemp, mServiceConnect, Service.BIND_AUTO_CREATE);

		if (ChatService.getInstance().getEnterFromNotification()) {
			int id_x = ChatService.getInstance().getEnterFromNotificationId();
			ChatService.getInstance().setType(2);
			ChatService.getInstance().setId(id_x);
			ChatServiceData.getInstance().clearUnreadMsgs(id_x);

			try {
				ChatActivity preced = ConnectedApp.getInstance().getChatActivity();
				preced.finish();
			} catch (Exception e) {
			}
		}

		ConnectedApp.getInstance().setChatActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		ChatActivity.mIsActive = false;
		unbindService(mServiceConnect);
	}

	public static boolean getIsActive() {
		return mIsActive;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	/**
	 * update the history listview with the coming of new message
	 */
	public void updateListviewHistory(List<ChatEntity> msgs, List<Boolean> isSelf, int type, String name) {
		mListviewAdapter = new ChatListviewAdapter(this, this, msgs, isSelf);
		mListviewHistory.setAdapter(mListviewAdapter);

		mListviewHistory.setSelection(msgs.size() - 1);

		if (type == 2) {
			mTvTop.setText(name);
		}
	}
}

class ToDisplayHistory extends Thread {
	private Handler mHandler;

	public ToDisplayHistory(Handler hl0) {
		mHandler = hl0;
	}

	@Override
	public void run() {
		try {
			sleep(15);
		} catch (Exception e) {
		}
		Message message = new Message();
		message.what = 1;
		mHandler.sendMessage(message);
	}
}
