package com.R.welcome;

import com.RTalk.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class GuideActivity extends FragmentActivity {
	private ViewPager vPager;
	private RadioGroup rGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		vPager = (ViewPager) findViewById(R.id.vPager);
		vPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		rGroup = (RadioGroup) findViewById(R.id.rGroup);
		rGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rBtn1:
					vPager.setCurrentItem(0);
					break;
				case R.id.rBtn2:
					vPager.setCurrentItem(1);
					break;
				case R.id.rBtn3:
					vPager.setCurrentItem(2);
					break;
				case R.id.rBtn4:
					vPager.setCurrentItem(3);
					break;

				default:
					break;
				}
			}
		});
		vPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					rGroup.check(R.id.rBtn1);
					break;
				case 1:
					rGroup.check(R.id.rBtn2);
					break;
				case 2:
					rGroup.check(R.id.rBtn3);
					break;
				case 3:
					rGroup.check(R.id.rBtn4);
					break;

				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	class MyAdapter extends FragmentStatePagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment fragment = null;
			switch (arg0) {
			case 0:
				fragment = new Guide1Fragment();
				break;
			case 1:
				fragment = new Guide2Fragment();
				break;
			case 2:
				fragment = new Guide3Fragment();
				break;
			case 3:
				fragment = new Guide4Fragment();
				break;

			default:
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}

	}

}
