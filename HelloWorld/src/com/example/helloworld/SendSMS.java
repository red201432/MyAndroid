package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;

public class SendSMS extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smsactivity);
	}
	public void onClick_SendSMS(View v){
		sendSMS("5556","Hello World!!!");
	}
	@SuppressWarnings("deprecation")
	private void sendSMS(String phoneNumber,String msg){
		SmsManager sms=SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, msg, null, null);
	}
	
}
