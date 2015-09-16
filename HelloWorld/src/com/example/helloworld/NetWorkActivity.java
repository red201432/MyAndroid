package com.example.helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class NetWorkActivity extends Activity {
	ImageView img;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.networkactivity);
		//new DownloadImageTask().execute("http://www.mayoff.com/5-01cablecarDCP01934.jpg");
		//new AccessWebServiceTask().execute("apple");
		new ReadJSONFeedTask().execute("http://extjs.org.cn/extjs/examples/grid/survey.heml");
	}
	private InputStream OpenHttpConnection(String urlString) throws IOException{
		InputStream in=null;
		int response=-1;
		URL url=new URL(urlString);
		URLConnection conn=url.openConnection();
		if(!(conn instanceof HttpURLConnection)){
			throw new IOException("Not an HTTP connection");
		}
			try{
				HttpURLConnection httpConn=(HttpURLConnection)conn;
				httpConn.setAllowUserInteraction(false);
				httpConn.setInstanceFollowRedirects(true);
				httpConn.setRequestMethod("GET");
				httpConn.connect();
				
				response=httpConn.getResponseCode();
				if(response==HttpURLConnection.HTTP_OK){
					in=httpConn.getInputStream();
				}
			} catch(Exception ex){
				Log.d("NetWorking ", ex.getLocalizedMessage());
				throw new IOException("Error conection");
			}
			return in;
		}
	private Bitmap DownLoadImage(String URL){
		Bitmap bitmap=null;
		InputStream in=null;
		
		try{
			in=OpenHttpConnection(URL);
			bitmap=BitmapFactory.decodeStream(in);
			in.close();
		} catch (IOException ex){
			Log.d("NetWorkingActivity", ex.getLocalizedMessage());
		}
		
		return bitmap;
	}
	/*
	 * ��ȡͼƬ
	 */
	private class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{
		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 * ���е���Ҫ�첽ִ�еĴ��붼����doInBackground�����ڣ���������ɺ�ͨ��onPostExcute()�������ؽ��
		 */
		protected Bitmap doInBackground(String... urls){
			return DownLoadImage(urls[0]);
		}
		
		protected void onPostExecute(Bitmap result){
			ImageView img=(ImageView)findViewById(R.id.img);
			img.setImageBitmap(result);
		}
	}
	
	/*
	 * ʹ��web���񣬷��ظ�ʽΪXML�ĵ�
	 */
	private String WordDefinition(String word){
		InputStream in=null;
		String strDefinition="";
		try{
			//in=OpenHttpConnection("http://219.146.70.115/WebServices/Services/UserServer.asmx/UserLoginStr?userName=15192677809psw=123456");
			in=OpenHttpConnection("http://services.aonaware.com/DictService/DictService.asmx/Define?word="+word);
			Document doc=null;
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			try{
				db=dbf.newDocumentBuilder();
				doc=db.parse(in);
			}catch (ParserConfigurationException e){
				e.printStackTrace();
			}catch (Exception ex){
				ex.printStackTrace();
			}
			doc.getDocumentElement().normalize();
			NodeList definitionElements=doc.getElementsByTagName("Definition");
			for(int i=0;i<definitionElements.getLength();i++){
				Node itemNode=definitionElements.item(i);
				if(itemNode.getNodeType()==Node.ELEMENT_NODE){
					Element definitionElement=(Element)itemNode;
					
					NodeList wordDefinitionElements= (definitionElement).getElementsByTagName("WordDefinition");
					strDefinition="";
					 for(int j=0;j<wordDefinitionElements.getLength();j++){
						 
						 Element wordDefinitionElement=(Element)wordDefinitionElements.item(j);
						 
						 NodeList textNodes=((Node)wordDefinitionElement).getChildNodes();
						 
						 strDefinition += ((Node)textNodes.item(0)).getNodeValue()+".\n";
					 }
				}
			}			
		}catch (IOException e){
		Log.d("NetworkActivity", e.getLocalizedMessage());
		}
		
		return strDefinition;
	}
	
	private class AccessWebServiceTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			return WordDefinition(urls[0]);
		}
		protected void onPostExecute(String result){
			Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
		}		
	}
	
	public String readJSONFeed(String url){
		StringBuilder stringBuilder=new StringBuilder();
		HttpClient client=new DefaultHttpClient();
		HttpGet httpGet=new HttpGet(url);
		try{
			HttpResponse response=client.execute(httpGet);
			StatusLine statusLine=response.getStatusLine();
			int statusCode=statusLine.getStatusCode();
			if(statusCode==200){
				HttpEntity entity=response.getEntity();
				InputStream content=entity.getContent();
				BufferedReader reader=new BufferedReader(new InputStreamReader(content));
				String line;
				while((line=reader.readLine())!=null){
					stringBuilder.append(line);
				}
			}else {
				Log.e("JSON", "Failed to download file");
			}
			
		} catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
	
	private class ReadJSONFeedTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return readJSONFeed(params[0]);
		}
		protected void onPostExecute(String result){
			try{
				JSONArray jsonArray=new JSONArray(result);
				Log.i("JSON", "Number of surveys in feed:"+jsonArray.length());
				
				for(int i=0 ;i<jsonArray.length();i++){
					JSONObject jsonObject=jsonArray.getJSONObject(i);
					Toast.makeText(getBaseContext(), 
							jsonObject.getString("appId")+"-" +jsonObject.getString("inputTime"), 
							Toast.LENGTH_LONG).show();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}	
}