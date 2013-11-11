package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLhelper extends SQLiteOpenHelper {

	public static final String DBName = "paiwen_db";
	public static final int version = 1;
	public static final String shiping_downloaded = "shiping_downloaded";// 视频下载成功
	public static final String shiping_downloading = "shiping_downloading";// 视频正在下载
	public static final String filedownlog = "filedownlog";        //多线程下载
	public SQLhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	public SQLhelper(Context context) {
		super(context, DBName, null, version);

	}

	public void onCreate(SQLiteDatabase db) {
		//下载表
		
		db.execSQL("create table shiping_downloaded (_id integer primary key"
				+ " autoincrement,title text,size text,path text,progress int default 0);");
		db.execSQL("create table shiping_downloading (_id integer primary key"
				+ " autoincrement,title text,size text,path text,progress int default 0,kaiguan INTEGER default 0);");
		
		//多线程下载表
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog0 (id integer primary " +
				"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog1 (id integer primary " +
				"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog2 (id integer primary " +
				"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog3 (id integer primary " +
				"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog4 (id integer primary " +
				"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog5 (id integer primary " +
				"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog6 (id integer primary " +
				"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog7 (id integer primary " +
				"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog8 (id integer primary " +
				"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog9 (id integer primary " +
				"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS downloading (id integer primary " +
				"key autoincrement, Kaiguan boolean , ShuZi integer)");
		
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS shiping_downloaded");
		db.execSQL("DROP TABLE IF EXISTS shiping_downloading");
		db.execSQL("DROP TABLE IF EXISTS filedownlog0");
		db.execSQL("DROP TABLE IF EXISTS filedownlog1");
		db.execSQL("DROP TABLE IF EXISTS filedownlog2");
		db.execSQL("DROP TABLE IF EXISTS filedownlog3");
		db.execSQL("DROP TABLE IF EXISTS filedownlog4");
		db.execSQL("DROP TABLE IF EXISTS filedownlog5");
		db.execSQL("DROP TABLE IF EXISTS filedownlog6");
		db.execSQL("DROP TABLE IF EXISTS filedownlog7");
		db.execSQL("DROP TABLE IF EXISTS filedownlog8");
		db.execSQL("DROP TABLE IF EXISTS filedownlog9");
		db.execSQL("DROP TABLE IF EXISTS downloading");
		
		
		//下载表
		
				db.execSQL("create table shiping_downloaded (_id integer primary key"
						+ " autoincrement,title text,size text,path text,progress int default 0);");
				db.execSQL("create table shiping_downloading (_id integer primary key"
						+ " autoincrement,title text,size text,path text,progress int default 0,kaiguan INTEGER default 0);");
				
				//多线程下载表
				db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog0 (id integer primary " +
						"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
				db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog1 (id integer primary " +
						"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
				db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog2 (id integer primary " +
						"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
				db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog3 (id integer primary " +
						"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
				db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog4 (id integer primary " +
						"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
				db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog5 (id integer primary " +
						"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
				db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog6 (id integer primary " +
						"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
				db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog7 (id integer primary " +
						"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
				db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog8 (id integer primary " +
						"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
				db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog9 (id integer primary " +
						"key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
				db.execSQL("CREATE TABLE IF NOT EXISTS downloading (id integer primary " +
						"key autoincrement, Kaiguan boolean , ShuZi integer)");
		
	}

}
