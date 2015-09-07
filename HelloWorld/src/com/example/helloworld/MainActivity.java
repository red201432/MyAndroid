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
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	CharSequence[] items={"Google","Apple","Microsoft"};
	boolean[] checkedItems=new boolean [items.length];
	ProgressDialog progressDialog;
	String tag="Lifecycle";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		Log.d(tag, "In the onCreate() event");
	}
	
	public void onClick(View v){
		showDialog(0);
	}
	
	@Override
	protected Dialog onCreateDialog(int id){
		switch(id){
		case 0:
			return new AlertDialog.Builder(this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("This is a dialog with some simple text ...")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int whichButton){
							Toast.makeText(getBaseContext(), "OK clicked!", Toast.LENGTH_SHORT).show();
						}						
					}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(getBaseContext(), "Cancel clicked!",Toast.LENGTH_SHORT).show();
						}
					}).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							// TODO Auto-generated method stub
							Toast.makeText(getBaseContext(), items[which]+(isChecked?"checked!":"unchecked!"), Toast.LENGTH_SHORT).show();
						}
					}).create();
		case 1:
			progressDialog=new ProgressDialog(this);
			progressDialog.setIcon(R.drawable.ic_launcher);
			progressDialog.setTitle("Downloading files...");
			progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getBaseContext(), "OK clicked!", Toast.LENGTH_SHORT).show();
				}
			});
			progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getBaseContext(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
				}
			});
			return progressDialog;
		}
		return null;
	}
	//显式等待窗口
	public void onClick2(View v){
		final ProgressDialog dialog =ProgressDialog.show(this, "Doing something", "Please wait ...",true);
		new Thread(new Runnable(){
			public void run(){
				try{
					Thread.sleep(5000);
					
					dialog.dismiss();
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	//显式详细的进度条
	public void onClick3(View v){
		showDialog(1);
		progressDialog.setProgress(0);
		
		//final ProgressDialog dialog =ProgressDialog.show(this, "Doing something", "Please wait ...",true);
		new Thread(new Runnable(){
			public void run(){
				for(int i=0;i<5;i++){
				try{
					Thread.sleep(1000);
					progressDialog.incrementProgressBy((int)(100/15));
				}catch (InterruptedException e){
					e.printStackTrace();
				}
				progressDialog.dismiss();
				}
			}
		}).start();
	}
	
	
	public void onStart(){
		super.onStart();
		Log.d(tag, "In the onStart() event");
	}
	
	public void onStop(){
		super.onStop();
		Log.d(tag, "In the onStop() event");
			}
	
	public void onDestroy(){
		super.onDestroy();
		Log.d(tag, "In the onDestroy() event");
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
