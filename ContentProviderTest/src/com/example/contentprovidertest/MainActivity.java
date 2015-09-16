package com.example.contentprovidertest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
     LinearLayout ll = new LinearLayout(this);
     ll.setOrientation(LinearLayout.VERTICAL);  //设置排列方式为垂直
     LayoutParams layoutParams = new LayoutParams(
             LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
     TextView tv = new TextView(this);
     tv.setText("Hello, Android!");
     tv.setLayoutParams(layoutParams);

     DatePicker dp = new DatePicker(this);
     dp.setLayoutParams(layoutParams);
     ll.addView(tv);
     ll.addView(dp);
     setContentView(ll);
	}
//	public TextView tv;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		tv.setBackgroundColor(Color.BLUE);
//		setContentView(R.layout.activity_main);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
