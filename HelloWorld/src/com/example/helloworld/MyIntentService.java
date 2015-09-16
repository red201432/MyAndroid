package com.example.helloworld;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

	public MyIntentService() {
		super("MyIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		try{
			int result=DownloadFile(new URL("http://image.tianjimedia.com/uploadImages/2011/306/9M713OK3OC2W.jpg"));
			Log.d("IntentService","Download "+result+" bytes");
			Intent broadcastIntent=new Intent();
			broadcastIntent.setAction("File_download_Action");
			getBaseContext().sendBroadcast(broadcastIntent);
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
	}
	
	private int DownloadFile(URL url){
		try{
			Thread.sleep(5000);
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		return 100;
	}
	
}
