package com.example.criminalintent;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;

public class CrimeCameraActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeCameraFragment();
	}

}
