package com.example.helloworld;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
public class ThirdActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thirdactivity);
		
		Toast.makeText(this, getIntent().getStringExtra("str1"), Toast.LENGTH_SHORT).show();
		
		Toast.makeText(this,Integer.toString(getIntent().getIntExtra("age1",0)), Toast.LENGTH_SHORT).show();
		
		Bundle bundle=getIntent().getExtras();
		
		Toast.makeText(this, bundle.getString("str2"), Toast.LENGTH_SHORT).show();
		
		Toast.makeText(this,Integer.toString(bundle.getInt("age2")), Toast.LENGTH_SHORT).show();
		
	}
	public void onClick(View v){
		Intent i=new Intent();
		
		i.putExtra("age3", 45);
		
		i.setData(Uri.parse("发送给 主界面的 数据"));
		
		setResult(RESULT_OK,i);
		finish();		
	}
	
}
