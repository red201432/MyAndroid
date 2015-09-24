package com.example.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
/*
 * ���ظ��õ��Ĵ���������
 * */
public abstract class SingleFragmentActivity extends FragmentActivity {
	protected abstract android.support.v4.app.Fragment createFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/* 
		 *  �������ύһ��Fragment����
		 *  */
		FragmentManager fm=getSupportFragmentManager();
		Fragment fragment=fm.findFragmentById(R.id.fragmentContainer);
		if(fragment== null){
			fragment=createFragment();
			/*fragment���� add ��һ����������ID �ڶ����������´�����fragment*/
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}
}