package com.example.helloworld;
import android.os.Bundle;
import android.preference.PreferenceActivity;
public class AppPreferenceActivity extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.myappreferences);
	}
}
