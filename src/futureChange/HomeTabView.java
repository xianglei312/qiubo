package futureChange;

import com.RTalk.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by jason xiang.
 * 首页的tabBar
 */

public class HomeTabView extends RelativeLayout {

	private ImageView msgsImageView, contactsImageView, friendsImageView, settingsImageView;

	private LinearLayout msgsBtn, contactsBtn, settingsBtn, friendsBtn;

	private int selectedTab;
	// private List<Integer> defaultImgList;
	//
	// private List<Integer> chosenImgList;

	public interface OnCheckListener {
		
		void onChecked(View v);

	}

	OnCheckListener onCheckListener;

	public void setOnCheckListener(OnCheckListener onCheckListener) {

		this.onCheckListener = onCheckListener;

	}

	public HomeTabView(Context context) {

		super(context);

		init(context);

	}

	public HomeTabView(Context context, AttributeSet attrs) {

		super(context, attrs);

		init(context);

	}

	public HomeTabView(Context context, AttributeSet attrs, int defStyleAttr) {

		super(context, attrs, defStyleAttr);

		init(context);

	}

	private void init(Context context) {

		// fillImageList();

		LayoutInflater.from(context).inflate(R.layout.layout_home_tab_view, this, true);

		msgsImageView = (ImageView) findViewById(R.id.main_body_main_container_msgs);

		contactsImageView = (ImageView) findViewById(R.id.main_body_main_container_contacts);

		friendsImageView = (ImageView) findViewById(R.id.main_body_main_container_friends);

		settingsImageView = (ImageView) findViewById(R.id.main_body_main_container_settings);

		msgsBtn = (LinearLayout) findViewById(R.id.ll_main_body_main_container_msgs);

		contactsBtn = (LinearLayout) findViewById(R.id.ll_main_body_main_container_contacts);

		friendsBtn = (LinearLayout) findViewById(R.id.ll_main_body_main_container_friends);

		settingsBtn = (LinearLayout) findViewById(R.id.ll_main_body_main_container_settings);

		selectedTab = 0;

		msgsImageView.setBackgroundResource(R.drawable.tab_weixin_pressed);

		contactsBtn.setOnClickListener(listener);

		msgsBtn.setOnClickListener(listener);

		settingsBtn.setOnClickListener(listener);

		friendsBtn.setOnClickListener(listener);

	}

	OnClickListener listener = new OnClickListener() {

		@Override

		public void onClick(View view) {

			if (onCheckListener != null) {

				switch (view.getId()) {

				case R.id.ll_main_body_main_container_msgs:

					if (selectedTab != 0) {

						msgsImageView.setBackgroundResource(R.drawable.tab_weixin_pressed);

						contactsImageView.setBackgroundResource(R.drawable.tab_address_normal);

						friendsImageView.setBackgroundResource(R.drawable.tab_find_frd_normal);

						settingsImageView.setBackgroundResource(R.drawable.tab_settings_normal);

						selectedTab = 0;

					}

					break;

				case R.id.ll_main_body_main_container_contacts:

					if (selectedTab != 1) {

						msgsImageView.setBackgroundResource(R.drawable.tab_weixin_normal);

						contactsImageView.setBackgroundResource(R.drawable.tab_address_pressed);

						friendsImageView.setBackgroundResource(R.drawable.tab_find_frd_normal);

						settingsImageView.setBackgroundResource(R.drawable.tab_settings_normal);

						selectedTab = 1;

					}

					break;

				case R.id.ll_main_body_main_container_friends:

					if (selectedTab != 2) {

						msgsImageView.setBackgroundResource(R.drawable.tab_weixin_normal);

						contactsImageView.setBackgroundResource(R.drawable.tab_address_normal);

						friendsImageView.setBackgroundResource(R.drawable.tab_find_frd_pressed);

						settingsImageView.setBackgroundResource(R.drawable.tab_settings_normal);

						selectedTab = 2;

					}

					break;

				case R.id.ll_main_body_main_container_settings:

					if (selectedTab != 3) {

						msgsImageView.setBackgroundResource(R.drawable.tab_weixin_normal);

						contactsImageView.setBackgroundResource(R.drawable.tab_address_normal);

						friendsImageView.setBackgroundResource(R.drawable.tab_find_frd_normal);

						settingsImageView.setBackgroundResource(R.drawable.tab_settings_pressed);

						selectedTab = 3;

					}

					break;

				}

				onCheckListener.onChecked(view);

			}

		}

	};

	// private void fillImageList() {
	//
	// defaultImgList = new ArrayList<>();
	//
	// chosenImgList = new ArrayList<>();
	//
	// defaultImgList.add(R.drawable.ic_btm_home);
	//
	// defaultImgList.add(R.drawable.ic_btm_task);
	//
	// defaultImgList.add(R.drawable.ic_btm_user);
	//
	// defaultImgList.add(R.drawable.ic_btm_farminghand);
	//
	// chosenImgList.add(R.drawable.ic_btm_home_chosen);
	//
	// chosenImgList.add(R.drawable.ic_btm_task_chosen);
	//
	// chosenImgList.add(R.drawable.ic_btm_user_chosen);
	//
	// chosenImgList.add(R.drawable.ic_btm_farminghand_chosen);
	//
	// }

}
