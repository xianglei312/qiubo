
package org.yuner.www.chatter;

import java.util.List;

import org.yuner.www.bean.ChatEntity;

import com.RTalk.R;
import com.baidu.baidutranslate.openapi.entity.TransResult;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ChatListviewAdapter extends BaseAdapter {

	
	
	TransResult result;


	private List<ChatEntity> mVector;
	private List<Boolean> mVectorIsSelf;
	private LayoutInflater mInflater;
	private Context mContext0;
	SpannableString spannableString;
	
	private LayoutInflater inflater = null;	//用于显示popupWindow
	private PopupWindow popupWindow;	//长按复制窗口
	private TextView copyTv, deleteTv;	//复制键，删除键
	private static final int SHOW_TIME = 1000;	//toast响应时间1000毫秒

	public ChatListviewAdapter(ChatActivity par, Context context, List<ChatEntity> vector, List<Boolean> vectorIsSelf) {
		this.mVector = vector;
		mInflater = LayoutInflater.from(context);
		mContext0 = context;
		mVectorIsSelf = vectorIsSelf;
		inflater = (LayoutInflater) mContext0.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initPopupWindow(inflater);
	}

	public View getView(final int position, View convertView, ViewGroup root) {
		ImageView avatar;
		TextView content;
		TextView tvTranslate;
		TextView NameOfSpeaker;

		ChatEntity ent0 = mVector.get(position);
		String name = ent0.getName();
		String time = ent0.getTime();
		int sex = ent0.getSex();
		String real_msg = ent0.getContent();
		String tvTranslateText = ent0.gettvTranslat();

		if (mVectorIsSelf.get(position).booleanValue()) {
			convertView = mInflater.inflate(R.layout.cb0_chat_listview_item_right, null);
			content = (TextView) convertView.findViewById(R.id.cb0ChatListviewMsgRight);
			NameOfSpeaker = (TextView) convertView.findViewById(R.id.cb0ChatListviewNameRight);
			avatar = (ImageView) convertView.findViewById(R.id.cb0ChatListviewAvatarRight);

			String zhengze = "f0[0-9]{2}|f10[0-7]"; // 正则表达式，用来判断消息内是否有表情
			try {
				spannableString = ChatEmoticonUtil.getExpressionString(mContext0, real_msg, zhengze);
				content.setText(spannableString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			NameOfSpeaker.setText(time);

			int avatarId = ent0.getSenderAvatarid();
			if (avatarId == 0)
				avatar.setImageResource(R.drawable.cb0_h001);
			else
				avatar.setImageResource(R.drawable.cb0_h003);
		} else {
			convertView = mInflater.inflate(R.layout.cb0_chat_listview_item_left, null);

			content = (TextView) convertView.findViewById(R.id.cb0ChatListviewMsgLeft);
			tvTranslate = (TextView) convertView.findViewById(R.id.tvTranslate);

			String zhengze = "f0[0-9]{2}|f10[0-7]"; // 正则表达式，用来判断消息内是否有表情
			try {
				spannableString = ChatEmoticonUtil.getExpressionString(mContext0, real_msg, zhengze);
				content.setText(spannableString);
				
				tvTranslate.setText(tvTranslateText);


			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			NameOfSpeaker = (TextView) convertView.findViewById(R.id.cb0ChatListviewNameLeft);

			NameOfSpeaker.setText(name + " " + time);

			avatar = (ImageView) convertView.findViewById(R.id.cb0ChatListviewAvatarLeft);
			int avatarId = ent0.getSenderAvatarid();
			if (avatarId == 0)
				avatar.setImageResource(R.drawable.cb0_h001);
			else
				avatar.setImageResource(R.drawable.cb0_h003);

		}
		
		content.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				showPop(v);
				copyTv.setOnTouchListener(new tvOnTouch(mContext0, position));
				deleteTv.setOnTouchListener(new tvOnTouch(mContext0, position));
				return false;
			}
		});
		return convertView;

	}
	
	/**
	 * 初始化Popupwindow
	 * 
	 * @param inflater
	 */
	private void initPopupWindow(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.pop_item_layout, null);
		popupWindow = new PopupWindow(view, 200, 100);
		copyTv = (TextView) view.findViewById(R.id.pop_copy_tv);
		deleteTv = (TextView) view.findViewById(R.id.pop_delete_tv);
	}

	/**
	 * 触摸事件
	 * 
	 * @author zihao
	 * 
	 */
	class tvOnTouch implements OnTouchListener {
		private Context mContext;
		private int mPosition;

		public tvOnTouch(Context context, int position) {
			// TODO Auto-generated method stub
			this.mContext = context;
			this.mPosition = position;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.pop_copy_tv) {
				TextView tv = (TextView) v;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {// 按下
					tv.setTextColor(0xff00CD66);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开
					tv.setTextColor(0xffffffff);
					TextManager.copyText(mContext, mVector.get(mPosition).getContent());
					Toast.makeText(mContext, "复制成功", SHOW_TIME).show();

					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			} else {
				TextView tv = (TextView) v;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {// 按下
					tv.setTextColor(0xff00CD66);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {// 松开
					tv.setTextColor(0xffffffff);
					Toast.makeText(mContext, "删除功能待实现", SHOW_TIME).show();

					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			}
			return true;
		}

	}

	/**
	 * Popupwindow显示
	 * 
	 * @param v
	 */
	@SuppressWarnings("deprecation")
	private void showPop(View v) {
		popupWindow.setFocusable(false);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());// 设置此项可点击Popupwindow外区域消失，注释则不消失

		// 设置出现位置
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] + v.getWidth() / 2 - popupWindow.getWidth() / 2,
				location[1] - popupWindow.getHeight());
	}

	public int getCount() {
		return mVector.size();
	}

	public Object getItem(int position) {
		return mVector.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	



}