package com.example.helloworld;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
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

public class SecondActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondactivity);
	}
	
	public void onClick(View view){
		Intent data=new Intent();
		EditText txt_username=(EditText)findViewById(R.id.txt_username);
		
		data.setData(Uri.parse(txt_username.getText().toString()));
		
		setResult(RESULT_OK,data);
		 finish();
	}
}
