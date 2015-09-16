package com.example.contentaccesstest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * 
 * @author chenzheng_java
 * @description ͨ�����������ṩ�߽�����ɾ���.ע�Ȿ������Ϊ�˷����Ķ���
 * ����Ҫ���ݿ������ĵط�ֱ��д�������ݿ����ֶε����ƣ�ʵ�������ǲ������ģ�
 * ��Ϊ�����ṩ�ߵ�ʹ���ߣ����ǲ�������ʹ����������ṩ��֮ǰ��ȥ�˽�sqlite
 * �б��Ľṹ���Ƚ����˵������ǣ��������ṩ���н�Ը���ṩ���ⲿ���ʵ��ֶ����ƣ�������
 * ����Ϊstring final �ĳ�����
 */
public class MainActivity extends Activity {
	private final static String tag = "֪ͨ";
	private TextView textView;
	String result = "���:/n";
	ContentResolver reslover;
	Uri uri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/**
		 * ��������һ��Ҫ�������uri�����ݵ��׺������ṩ�����ĸ��ط�һһ��Ӧ
		 * ��MyContentProvider������������Ƭ��
		 * uriMatcher.addURI(authority, "path_chenzheng", 1);// ������ǰ���е����еļ�¼
		uriMatcher.addURI(authority, "path_chenzheng/#", 2);// ������ǰ���е�ĳ���ض��ļ�¼����¼id����#��������
		����authorityΪcn.com.chenzheng_java.hello��
		 */
		uri = Uri.parse("content://cn.com.chenzheng_java.hello/path_chenzheng");
		
		/**
		 * �����ṩ����ʲô�������ṩ���൱��һ����װ������ɾ�Ĳ�����Ľӿڣ�����ӿ���һ������ֻ��Я��Կ�׵ķ����߲��ܷ��ʡ�
		 * ContentResolver��ʲô��ContentResolver��һ������������Я����Կ��(Կ�����б�ǩ��ʾ�����Ǹ��ŵ�Կ�ף���path_chenzheng)
		 * ȥѰ�������ṩ�ߣ�Ȼ����������ṩ�ߵ���ɾ��ķ���
		 * �����������contentResolver����ɾ��ľ��൱�ڽ����񽻸���������
		 * Ȼ��������ȥ���ܴ򿪵������ṩ�ߣ�����ִ��������Ӧ�ķ����������������.
		 * ContentResolver�ĺô����ڣ����ǿ�������CotentProvider�ľ���ʵ�֣�����contentProvider���������ʵ�ֵģ�����ִ��
		 * ĳһ������ʱ����Ҫ��д�Ĵ��붼��һ���ġ�
		 */
		reslover = this.getContentResolver();
		textView = (TextView) findViewById(R.id.textView);

		Button insertButton = (Button) findViewById(R.id.insertButton);
		insertButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				insert(reslover, uri);
			}
		});

		Button deleteButton = (Button) findViewById(R.id.deleteButton);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				delete(reslover, uri);
			}
		});
		
		Button updateButton = (Button) findViewById(R.id.updateButton);
		updateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				update(reslover, uri);
			}
		});
		
		Button queryButton = (Button) findViewById(R.id.queryButton);
		queryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				query(reslover, uri);
			}
		});
		
	}

	private void insert(ContentResolver resolver, Uri uri) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", "��С��");
		contentValues.put("age", 22);
		contentValues.put("isMan", true);
		Uri uri2 = resolver.insert(uri, contentValues);
		Log.i(tag, "����ɹ���");
		result += "�ɹ�������һ����¼,uriΪ" + uri2;
		textView.setText(result);
		result = "";
	}

	private void update(ContentResolver resolver, Uri uri) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("age", 122);
		int number = resolver.update(uri, contentValues, null, null);
		Log.i(tag, "���³ɹ���");
		result += "�ɹ�������" + number+"����¼";
		textView.setText(result);
		result = "";

	}
	private void delete(ContentResolver resolver, Uri uri) {
		String where = " 1=1 and isMan=?";
		//����Ҫע��Ŷ��sqlite���ݿ�����û��boolean�ģ�true�ᱻת��1�洢
		String[] selectionArgs = new String[] { "1" };
		int number = resolver.delete(uri, where, selectionArgs);
		Log.i(tag, "ɾ���ɹ���");
		textView.setText(result + "�ɹ�ɾ����" + number + "����¼");
		result = "";
	}

	private void query(ContentResolver resolver, Uri uri) {

		String[] projection = new String[] { "id", "name", "age", "isMan" };
		Cursor cursor = resolver.query(uri, projection, null, null, null);
		int count = cursor.getCount();
		Log.i(tag, "�ܼ�¼��" + count);

		int idIndex = cursor.getColumnIndex("id");
		int nameIndex = cursor.getColumnIndex("name");
		int ageIndex = cursor.getColumnIndex("age");
		int isManIndex = cursor.getColumnIndex("isMan");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(idIndex);
			String name = cursor.getString(nameIndex);
			int age = cursor.getInt(ageIndex);
			int isMan = cursor.getInt(isManIndex);
			Log.i(tag, "id=" + id + " name=" + name + " age=" + age + " isMan="
					+ isMan);
			result += "id=" + id + " name=" + name + " age=" + age + " isMan="
					+ isMan;
			cursor.moveToNext();
		}

		textView.setText(result);
		result = "";

	}

}