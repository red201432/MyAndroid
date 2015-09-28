package com.example.photogallery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.R.string;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

public class FlickFetchr {
	private static final String TAG="PhotoGalleryGragment";
	/*此方法获取数据
	 * 
	 */
	byte[] getUrlBytes(String urlSpec) throws IOException{
		URL url=new URL(urlSpec);
		/*HttpURLConnection对象虽然提供了一个连接，只有在调用getInputStream()方法是时才会真正的连接到
		 * 指定的URL地址，在此之前我们无法获取有效的返回码
		 * 
		 * 创建URL并打开了网络连接，循环调用read（）方法读取网络连接到的数据
		 */
		HttpURLConnection connection=(HttpURLConnection)url.openConnection();
		try {
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			InputStream in=connection.getInputStream();
			if (connection.getResponseCode()!=HttpURLConnection.HTTP_OK) {
				return null;
			}
			
			int bytesRead=0;
			byte[] buffer=new byte[1024];
			while((bytesRead=in.read(buffer))>0){
				out.write(buffer,0,bytesRead);
			}
			out.close();
			return out.toByteArray();
		} finally{
			connection.disconnect();
		}
	}
	
	public String getUrl(String urlSpec) throws IOException {
		return new String(getUrlBytes(urlSpec));
	}
	
	
}
