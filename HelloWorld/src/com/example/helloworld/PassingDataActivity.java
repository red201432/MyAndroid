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
import android.util.Log;
import android.view.View;
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
import android.util.Log;
import android.view.View;
public class PassingDataActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	
	public void onClick(View v){
	Intent i=new Intent("com.example.helloworld.ThirdActivity");
	i.putExtra("str1", "这时一个字符串");
	i.putExtra("age1", 25);
	
	
	Bundle extras=new Bundle();
	extras.putString("str2", "另一个字符串");
	extras.putInt("age2", 35);
	
	i.putExtras(extras);
	
	
	startActivityForResult(i,1);
	
	}
	
	public void onActivityResult(int requestCode,int resultCode,Intent data){
		if(requestCode==1){
			if(resultCode==RESULT_OK){
				Toast.makeText(this, Integer.toString(data.getIntExtra("age3", 0)), 
						Toast.LENGTH_SHORT).show();
				
				Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
