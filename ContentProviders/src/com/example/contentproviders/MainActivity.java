package com.example.contentproviders;

import android.app.Activity;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void onClickAddTitle(View view){
		ContentValues values=new ContentValues();
		values.put(BooksProvider.TITLE, ((EditText)findViewById(R.id.txtTitle)).getText().toString());
		values.put(BooksProvider.ISBN, ((EditText)findViewById(R.id.txtISBN)).getText().toString());
		Uri uri=getContentResolver().insert(BooksProvider.CONTENT_URI, values);
		
		Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_SHORT).show();
	}
	
	public void onClickRetrieveTitle(View v){
		Uri allTitles=Uri.parse("content://com.example.helloworld.BooksProvider/books");
		
		Cursor c;
		if(android.os.Build.VERSION.SDK_INT<11){
			c=managedQuery(allTitles,null,null,null,"title desc");
			
		} else {
			CursorLoader cursorLoader=new CursorLoader(this,allTitles,null,null,null,"title desc");
			c=cursorLoader.loadInBackground();
		}
		if(c.moveToFirst()){
			do{
				Toast.makeText(this,
						c.getString(c.getColumnIndex(BooksProvider._ID))
						+", "+c.getString(c.getColumnIndex(BooksProvider.TITLE))
						+" , "+c.getString(c.getColumnIndex(BooksProvider.ISBN)), Toast.LENGTH_SHORT).show();
				
			}while(c.moveToNext());
		}
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
