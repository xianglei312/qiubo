package org.yuner.www.mainBody;

import org.yuner.www.bean.SearchEntity;

import com.RTalk.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class SearchFriendByNameActivity extends Activity {

	private EditText mSearchEtName;
	private Button mBtnSearchByName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.search_friend_by_name);

		mSearchEtName = (EditText) findViewById(R.id.search_friend_by_name_edit_name);
		mBtnSearchByName = (Button) findViewById(R.id.search_friend_by_name_btn_search);

		mBtnSearchByName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String searchName = mSearchEtName.getText().toString();
				SearchEntity s_ent0 = new SearchEntity(SearchEntity.SEARCH_BY_NAME, -1, -1, -1, searchName);
				MainBodyActivity.getInstance().startSearch(s_ent0);
			}
		});
	}

}
