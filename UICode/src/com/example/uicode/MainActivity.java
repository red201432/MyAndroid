package com.example.uicode;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		android.widget.LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		
		LinearLayout layout=new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		TextView tv=new TextView(this);
		tv.setText("This is a TextView");
		tv.setLayoutParams(params);
		
		
		Button btn=new Button(this);
		btn.setText("This is a Button ");
		btn.setBackgroundColor(CONTEXT_IGNORE_SECURITY);
		btn.setLayoutParams(params);
		
		
		layout.addView(tv);
		layout.addView(btn);
		
		LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		
		this.addContentView(layout, layoutParams);
		
		//setContentView(R.layout.activity_main);
	}

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
}