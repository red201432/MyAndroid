package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class EmailActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendemailactivity);
	}
	
	public void onClick_SendEmail(View v){
		String[] to={"someguy@example.com","anotherguy@example.com"};
		String[] cc={"busybody@example.com"};
		sendEmail(to,cc,"Hello","Hello my friends");
		
	}
	
	private void sendEmail(String[] emailAddress,String[] carbonCopies,String subject,String message){
		Intent emailIntent=new Intent(Intent.ACTION_SEND);
		emailIntent.setData(Uri.parse("mailTo:"));
		String[] to=emailAddress;
		String[] cc=carbonCopies;
		emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_CC, cc);
		emailIntent.putExtra(Intent.EXTRA_TEXT, message);
		emailIntent.setType("message/rfc822");
		startActivity(Intent.createChooser(emailIntent, "Email"));
	}
}
