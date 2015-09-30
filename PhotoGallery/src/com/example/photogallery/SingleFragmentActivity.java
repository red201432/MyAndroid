package com.example.photogallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
/*
 * 将重复用到的代码抽象出来
 * */
public abstract class SingleFragmentActivity extends FragmentActivity {
	protected abstract android.support.v4.app.Fragment createFragment();
	
	protected int  getLayoutResId() {
		return R.layout.activity_main;
	}	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setContentView(getLayoutResId());
		/* 
		 *  创建并提交一个Fragment事务
		 *  */
		FragmentManager fm=getSupportFragmentManager();
		Fragment fragment=fm.findFragmentById(R.id.fragmentContainer);
		if(fragment== null){
			fragment=createFragment();
			/*fragment事务 add 第一参数是容器ID 第二个参数是新创建的fragment*/
			fm.beginTransaction()
			.add(R.id.fragmentContainer, fragment)
			.commit();
		}
	}
}
