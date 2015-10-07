package com.R.welcome;

import org.yuner.www.MainActivity;

import com.RTalk.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Guide4Fragment extends Fragment {

	Button imgStart;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.guide4, container, false);
		imgStart = (Button) view.findViewById(R.id.imgStart);
		imgStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), MainActivity.class));
				getActivity().finish();
			}

		});

		return view;

	}

}
