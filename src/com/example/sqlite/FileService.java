package com.example.sqlite;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * ҵ��bean
 *
 */
public class FileService {
	private SQLhelper openHelper;
	SQLiteDatabase db ;
	public FileService(Context context) {
		openHelper = new SQLhelper(context);
	    db = openHelper.getWritableDatabase();
	}
	/**
	 * ��ȡÿ���߳��Ѿ����ص��ļ�����
	 * @param path
	 * @return
	 */
	public Map<Integer, Integer> getData(String path,int position){
		Cursor cursor = db.rawQuery("select threadid, downlength from filedownlog"+position+" where downpath=?", new String[]{path});
		Map<Integer, Integer> data = new HashMap<Integer, Integer>();
		while(cursor.moveToNext()){
			data.put(cursor.getInt(0), cursor.getInt(1));
		}
		cursor.close();
//		db.close();
		return data;
	}
	/**
	 * ����ÿ���߳��Ѿ����ص��ļ�����
	 * @param path
	 * @param map
	 */
	public void save(String path,  Map<Integer, Integer> map,int position){//int threadid, int 
		db.beginTransaction();
		try{
			for(Map.Entry<Integer, Integer> entry : map.entrySet()){
				db.execSQL("insert into filedownlog"+position+"(downpath, threadid, downlength) values(?,?,?)",
						new Object[]{path, entry.getKey(), entry.getValue()});
			}
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
//		db.close();
	}
	/**
	 * ʵʱ����ÿ���߳��Ѿ����ص��ļ�����
	 * @param path
	 * @param map
	 */
	public void update(String path, int threadId, int pos,int position){
		db.execSQL("update filedownlog"+position+" set downlength=? where downpath=? and threadid=?",
				new Object[]{pos, path, threadId});
//		db.close();
	}
	/**
	 * ���ļ�������ɺ�ɾ���Ӧ�����ؼ�¼
	 * @param path
	 */
	public void delete(String path,int position){
		db.execSQL("delete from filedownlog"+position+" where downpath=?", new Object[]{path});
//		db.close();
	}
	public void exit(){
		db.close();
	}
	
}
