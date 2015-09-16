package com.example.helloworld;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyServer extends Service {
	int counter=0;
	static final int UPDATE_INTERVAL=1000;
	private Timer timer=new Timer();
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent,int falgs,int startId){
		//Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
		try{
			int result=DownloadFile(new URL("http://www.amazon.com/somefile.pdf"));
			Toast.makeText(this, "Service started" +result+"bytes", Toast.LENGTH_SHORT).show();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
		dosomethingRepeatedly();
		try{
			new DoBackgroundTask().execute(new URL("http://product.pconline.com.cn/pdlib/guest/downloadPic4Guest_2009.jsp?isDownloadPic=true&picUrl=http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1312/17/c0/spcgroup/29713015_1387253809606_1600x1000.jpg&picName="),
					new URL("http://image.tianjimedia.com/uploadImages/2011/306/9M713OK3OC2W.jpg"),
					new URL("http://image.tianjimedia.com/uploadImages/2011/306/LQ3UO5P7E2TJ.jpg"));
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
		return START_STICKY;
	} 
	@Override
	public void onDestroy(){
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
	}
	
	private int DownloadFile(URL url){
		try{
			Thread.sleep(5000);
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		return 100;
	}
	
	private void dosomethingRepeatedly(){
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				Log.d("MYServer", String.valueOf(counter++));
			}
		}, 0, UPDATE_INTERVAL);		
	}
	
	private class DoBackgroundTask extends AsyncTask<URL,Integer,Long>{
		
		
		@Override
		protected Long doInBackground(URL... params) {
			// TODO Auto-generated method stub
			int count=params.length;
			long totalBytesDownloaded=0;
			for(int i=0;i<count;i++){
				totalBytesDownloaded+=DownloadFile(params[i]);
				
				publishProgress((int)(((i+1)/(float)count)*100));
			}
			return totalBytesDownloaded;
		}
		
		protected void onProgressUpdate(Integer... progress){
			Log.d("Downloading files", String.valueOf(progress[0])+"% downloaded");
			Toast.makeText(getBaseContext(), 
					String.valueOf(progress[0])+ "% downloaded",
					Toast.LENGTH_LONG
					).show();
		}
		
		protected void onPostExecute(Long result){
			Toast.makeText(getBaseContext(),
					"Downloaded "+result +"bytes", 
					Toast.LENGTH_LONG
					).show();
			stopSelf();
		}
		
	}
	
	
}
