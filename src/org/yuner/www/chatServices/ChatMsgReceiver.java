package org.yuner.www.chatServices;

import org.yuner.www.bean.ChatEntity;
import org.yuner.www.commons.GlobalMsgTypes;
import org.yuner.www.commons.GlobalStrings;

import com.baidu.baidutranslate.openapi.TranslateClient;
import com.baidu.baidutranslate.openapi.callback.ITransResultCallback;
import com.baidu.baidutranslate.openapi.entity.TransResult;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

public class ChatMsgReceiver extends BroadcastReceiver {
	private String msgStr;
	private Context mContext0;
	public static final String API_KEY = "Rd3xG6kv4teL3NnBx6qxwbBE";

	private String fromLang = "auto";// 源语言
	private String toLang = "auto";// 目标语言
	private static TranslateClient client;

	private ChatService mParService;

	public ChatMsgReceiver(ChatService mParService0) {
		mParService = mParService0;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		mContext0 = context;

		int msgType = intent.getIntExtra("yuner.example.hello.msg_type", 0);
		msgStr = intent.getStringExtra("yuner.example.hello.msg_received");

		switch (msgType) {
		case GlobalMsgTypes.msgPublicRoom:
		case GlobalMsgTypes.msgChattingRoom:
		case GlobalMsgTypes.msgFromFriend:

			final ChatEntity msgEntity = new ChatEntity(msgStr);

			Thread myTranslate = new Thread(new Runnable() {

				@Override
				public void run() {

					/**
					 * 
					 * 这里是翻译
					 */

					initTransClient();// 初始化翻译相关功能

					if (TextUtils.isEmpty(msgEntity.getContent()))
						return;

					client.translate(msgEntity.getContent(), fromLang, toLang, new ITransResultCallback() {

						@Override
						public void onResult(TransResult result) {// 翻译结果回调
							if (result == null) {

							} else {

								if (result.error_code == 0) {// 没错

									//msgEntity.settvTranslate(result.trans_result);

									Message msg = new Message();
									msg.what = 1;
									msg.obj = result.trans_result;
									handler.sendMessage(msg);

								} else {
									msgEntity.settvTranslate(result.error_msg);

								}

							}

						}
					});

				}
			});

			myTranslate.run();//翻译后新消息送达

			break;
		case GlobalMsgTypes.msgUpdateFriendList:
			// FriendListInfo.getFriendListInfo().updateFriendList(msgStr);
			break;
		case GlobalMsgTypes.msgFriendGoOnline:
			FriendListInfo.getFriendListInfo().friendGoOnAndOffline(msgStr, 1);
			break;
		case GlobalMsgTypes.msgFriendGoOffline:
			FriendListInfo.getFriendListInfo().friendGoOnAndOffline(msgStr, 0);
			break;
		default:
			break;
		}
	}

	private void initTransClient() {
		client = new TranslateClient(mContext0, API_KEY);

		// 这里可以设置为在线优先、离线优先、 只在线、只离线 4种模式，默认为在线优先。
		client.setPriority(TranslateClient.Priority.OFFLINE_FIRST);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case 1:
				String fanyi = (String) msg.obj;
				mParService.newMsgArrive(msgStr, false,fanyi);
				break;

			}
		};
	};

}