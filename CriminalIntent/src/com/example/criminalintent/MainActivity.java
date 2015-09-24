package com.example.criminalintent;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;

import java.util.UUID;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends FragmentActivity {

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		/* 
//		 *  创建并提交一个Fragment事务
//		 *  */
//		FragmentManager fm=getFragmentManager();
//		Fragment fragment=fm.findFragmentById(R.id.fragmentContainer);
//		if(fragment== null){
//			fragment=new CrimeFragment();
//			/*fragment事务 add 第一参数是容器ID 第二个参数是新创建的fragment*/
//			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
//		}
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//		setContentView(R.id.fragmentContainer);
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.menu.fragment_crime_list);
//	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		UUID crimeId=(UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		return CrimeFragment.newInstance(crimeId);
	}	
}
