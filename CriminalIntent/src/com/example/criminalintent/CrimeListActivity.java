package com.example.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeListFragment();
	}
	
	@Override
	protected int getLayoutResId(){
		return R.layout.activity_twopane;
	}	
}
