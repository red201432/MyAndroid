package com.example.helloworld;

import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.utils.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	CharSequence[] items={"Google","Apple","Microsoft"};
	boolean[] checkedItems=new boolean [items.length];
	ProgressDialog progressDialog;
	String tag="Lifecycle";
	int request_Code=1;
	TimePicker timePicker;
	static final int TIME_DIALOG_ID=5;
	int hour,minute;
	
	EditText textBox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager fragmentManager=getFragmentManager();
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
		
		//测试数据库
		DBAdapter db=new DBAdapter(this);
		db.open();
		long id=db.insertContact("Wei-MingLee","ceshi@126.com");
		id=db.insertContact("Mary Jackon","mary@163.com");
//		db.close();
		
		
		//测试结束
		WindowManager wm=getWindowManager();
		Display d=wm.getDefaultDisplay();
		
		if(d.getWidth()>d.getHeight()){
			Fragment1 fragment1=new Fragment1();
			fragmentTransaction.replace(android.R.id.content, fragment1);
		}
		else{
			Fragment2 fragment2=new Fragment2();
			fragmentTransaction.replace(android.R.id.content, fragment2);
		}
		//隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		Log.d(tag, "In the onCreate() event");
	}
	public void onClickShowNetWork(View v){
		startActivity(new Intent("com.example.helloworld.NetWorkActivity"));
	}
	public void onClickShowSendEmail(View v){
		startActivity(new Intent("com.example.helloworld.EmailActivity"));
	}
	
	public void onClickShowSendSMS(View v)
	{
		startActivity(new Intent("com.example.helloworld.SendSMS"));
	}
	public void showTimePicker(View v){
		setContentView(R.layout.timepicker1);
	}
	public void onTimePickerClick(View v){
		timePicker=(TimePicker) findViewById(R.id.timePicker);
		timePicker.setIs24HourView(true);
		Toast.makeText(getBaseContext(),"Time selected :"+timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute(),Toast.LENGTH_SHORT).show();
	}
	public void onClickShowService(View v){
		startActivity(new Intent("com.example.helloworld.ServicesActivity"));
	}
	public void onClick(View v){
		showDialog(0);
		startActivityForResult(new Intent("com.example.helloworld.SecondActivity"),request_Code);
	}
	
	public void showFragment(View v){
		setContentView(R.layout.testforrelativelayout);
	}
	@Override
	protected Dialog onCreateDialog(int id){
		switch(id){
//		case TIME_DIALOG_ID:
//			return new TimePickerDialog(this,mTimeSetListener,hour,minute,false);
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
	//
	private TimePickerDialog.OnTimeSetListener mTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			hour=hourOfDay;
			minute=minute;
			SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm:ss");
			Date date=new Date(0,0,0,hour,minute);
			String strDate=timeFormat.format(date);
			Toast.makeText(getBaseContext(), "you have selected"+strDate, Toast.LENGTH_SHORT).show();
		}
	};
	
	
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
	
	//显式第二个活动
	public void onClick4(View v){
		startActivity(new Intent("com.example.helloworld.SecondActivity"));
	}
	
	public void onClickThird(View v){
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
	//从意图返回结果
//	int request_Code=1;
//	public void onActivityResult(int requestCode,int resultCode,Intent data){
//		if(requestCode==request_Code){
//			if(resultCode==RESULT_OK){
//				Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT).show();
//			}
//		}
//	}
	public void showGallery(View v){
		//startActivity(new Intent("com.example.helloworld.GalleryActivity"));
		startActivity(new Intent("com.example.helloworld.ProviderActivity"));
	}
	public void  showPreference(View v){
		Intent i=new Intent("com.example.helloworld.AppPreferenceActivity");
		startActivity(i);
	}
	public void onModify(View v){
		SharedPreferences appPrefs=getSharedPreferences("com.example.helloworld_preferences",MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor=appPrefs.edit();
		prefsEditor.putString("editTextPref",((EditText)findViewById(R.id.txtString)).getText().toString());
		prefsEditor.commit();
	}	
	public void onDisplayText(View v){
		SharedPreferences appPrefs=getSharedPreferences("com.example.helloworld_preferences",MODE_PRIVATE);
		DisplayText(appPrefs.getString("editTextPref", ""));
	}
	private void DisplayText(String str){
		Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
	}
	
	public void onClickFile(View v){
		Intent i=new Intent("com.example.helloworld.FilesActivity");
		startActivity(i);
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
	public void onSaveInstanceState(Bundle outState){
		outState.putString("ID","123456");
		super.onSaveInstanceState(outState);
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
	
	//显示数据库中的数据
	public void showData(View v){
		DBAdapter db=new DBAdapter(this);
		db.open();
		Cursor c=db.getAllContacts();
		if(c.moveToFirst()){
			do{
				DisplayContact(c);
			}while(c.moveToNext());
		}
		db.close();
	}
	public void DisplayContact(Cursor c){
		Toast.makeText(this, "id: "+c.getString(0)+"\n Name: "
						+c.getString(1)+"\n Email: "+c.getString(2), Toast.LENGTH_SHORT).show();
	}
}
