package com.example.contentprovidertest;

//package cn.com.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;

/**
 * 
 * @author chenzheng_java
 * @description �Զ���������ṩ��.
 * 	�ܽ��·��������ṩ�ߵ���Ҫ���裺
 * ��һ������Ҫ��һ��uri,����൱�����ǵ���ַ������������ַ����ȥ���ʾ������վ
 * �ڶ�������ȥϵͳ��Ѱ�Ҹ�uri�е�authority����������Ϊ������ַ����
 * 		ֻҪ���ǵ������ṩ����manifest.xml�ļ���ע���ˣ���ôϵͳ�о�һ�����ڡ�
 * ������ͨ�������ṩ���ڲ���uriMatcher�����������֤�����ҵ����ˣ������У��һ��ÿ�������û��Ȩ�޷������أ���
 * ���ģ���֤ͨ���󣬾Ϳ��Ե��������ṩ�ߵ���ɾ��ķ������в�����
 */
public class MyContentProvider extends ContentProvider {
	// �Լ�ʵ�ֵ����ݿ����������
	private MyOpenHelper myOpenHelper;

	// ���ݿ������
	private SQLiteDatabase sqLiteDatabase;

	// uriƥ�����
	private static UriMatcher uriMatcher;

	// ��������(��һ�����ǿ������ȡ��)
	private static final String authority = "cn.com.chenzheng_java.hello";

	// ע��������ṩ��ƥ���uri
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		/*
		 * path_chenzheng���ֵ��ַ��������ȡ�ã�1��������������uri�뵱ǰ����
		 * ��ƥ��uri�����Ǻϣ�uriMathcher.match()�������ص�ֵ.#�����������֣�*���������ַ���
		 */
		uriMatcher.addURI(authority, "path_chenzheng", 1);// ������ǰ���е����еļ�¼
		uriMatcher.addURI(authority, "path_chenzheng/#", 2);// ������ǰ���е�ĳ���ض��ļ�¼����¼id����#��������
	}

	// ���ݱ��е�����ӳ��
	private static final String _id = "id";
	private static final String name = "name";
	private static final String age = "age";
	private static final String isMan = "isMan";

	/**
	 * @description �������ṩ�ߵ�һ�δ���ʱִ��
	 */
	@Override
	public boolean onCreate() {
		try {
			myOpenHelper = new MyOpenHelper(getContext(), DB_Name, null,
					Version_1);

		} catch (Exception e) {

			return false;
		}
		return true;
	}

	/**
	 * @description �����ݿ����ɾ��������ʱ��ִ��
	 *              android.content.ContentUriΪ���ǽ���uri��ص������ṩ�˿�ݷ����;��
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int number = 0;
		sqLiteDatabase = myOpenHelper.getWritableDatabase();
		int code = uriMatcher.match(uri);
		switch (code) {
		case 1:
			number = sqLiteDatabase
					.delete(Table_Name, selection, selectionArgs);
			break;
		case 2:
			long id = ContentUris.parseId(uri);
			/*
			 * ƴ��where�Ӿ�����Ŀ������ǲ����ط��˰��� ʵ���ϣ��������������Щ���ɵ�.
			 * if(selection==null||"".equals(selection.trim())) selection =
			 * " 1=1 and "; selection+=_id+"="+id;
			 * ƴ��where�Ӿ������鷳�ľ���and�����⣬��������ͨ������һ��1=1�����ĺ��ʽ�㽫��������
			 */
			selection = (selection == null || "".equals(selection.trim())) ? _id
					+ "=" + id
					: selection + " and " + _id + "=" + id;
			number = sqLiteDatabase
					.delete(Table_Name, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("�쳣����");
		}

		return number;
	}

	/**
	 *@description ��ȡ��ǰ�����ṩ�ߵ�MIME���� �������ͱ�������ǰ׺vnd.android.cursor.dir/���ò������⣩
	 *              ������¼��������ǰ׺vnd,android.cursor.item/���ò������⣩
	 *              �����˸÷���֮��ϵͳ���ڵ�һ������ʱ������֤����֤ͨ����ִ��crub����ʱ�����ظ�������֤��
	 *              �������û�ж���÷���������֤ʧ�ܣ�crub����ִ�е�ʱ��ϵͳ��Ĭ�ϵ�Ϊ������������֤���롣
	 */
	@Override
	public String getType(Uri uri) {
		int code = uriMatcher.match(uri);
		switch (code) {
		case 1:
			return "vnd.android.cursor.dir/chenzheng_java";
		case 2:
			return "vnd.android.cursor.item/chenzheng_java";
		default:
			throw new IllegalArgumentException("�쳣����");
		}

	}

	/**
	 * @description �����ݱ�����insertʱִ�и÷���
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		sqLiteDatabase = myOpenHelper.getWritableDatabase();
		int code = uriMatcher.match(uri);
		switch (code) {
		case 1:
			sqLiteDatabase.insert(Table_Name, name, values);
			break;
		case 2:
			long id = sqLiteDatabase.insert(Table_Name, name, values);
			// withAppendId��id���ӵ�uri�����
			ContentUris.withAppendedId(uri, id);
			break;
		default:
			throw new IllegalArgumentException("�쳣����");
		}

		return uri;
	}

	/**
	 * ��ִ�в�ѯʱ���ø÷���
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		sqLiteDatabase = myOpenHelper.getReadableDatabase();
		int code = uriMatcher.match(uri);
		switch (code) {
		case 1:
			cursor = sqLiteDatabase.query(Table_Name, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		case 2:
			// ��uri�н�����ID
			long id = ContentUris.parseId(uri);
			selection = (selection == null || "".equals(selection.trim())) ? _id
					+ "=" + id
					: selection + " and " + _id + "=" + id;
			cursor = sqLiteDatabase.query(Table_Name, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		default:
			throw new IllegalArgumentException("��������");
		}

		return cursor;
	}

	/**
	 * ��ִ�и��²�����ʱ��ִ�и÷���
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int num = 0;
		sqLiteDatabase = myOpenHelper.getWritableDatabase();
		int code = uriMatcher.match(uri);
		switch (code) {
		case 1:
			num = sqLiteDatabase.update(Table_Name, values, selection, selectionArgs);
			break;
		case 2:
			long id = ContentUris.parseId(uri);
			selection = (selection == null || "".equals(selection.trim())) ? _id
					+ "=" + id
					: selection + " and " + _id + "=" + id;
			num = sqLiteDatabase.update(Table_Name, values, selection, selectionArgs);
			break;
		default:
			break;
		}
		return num;
	}

	// ���ݿ�����
	private final String DB_Name = "chenzheng_java.db";
	// ���ݱ���
	private final String Table_Name = "chenzheng_java";
	// �汾��
	private final int Version_1 = 1;

	private class MyOpenHelper extends SQLiteOpenHelper {

		public MyOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		/**
		 * @description �����ݱ�������ʱ�����µı�
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = " create table if not exists " + Table_Name
					+ "(id INTEGER,name varchar(20),age integer,isMan boolean)";
			db.execSQL(sql);
		}

		/**
		 * @description ���汾����ʱ�����ķ���
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String sql = " drop table if exists " + Table_Name;
			db.execSQL(sql);
			onCreate(db);
		}

	}
}
