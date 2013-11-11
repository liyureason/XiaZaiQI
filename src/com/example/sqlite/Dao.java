package com.example.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao {
	private SQLiteDatabase db;
	private SQLhelper  helper;
	public Dao(Context context){
	      helper=new SQLhelper(context);
	      db=helper.getWritableDatabase();
	}
	public List<Map<String, Object>> findall(String dowmload_tableName){
    	Cursor c=db.query(dowmload_tableName, new String[]{"_id","title","size","path","progress"},null,null,null,null,null);
    	List<Map<String, Object>> lists=new ArrayList<Map<String, Object>>();
    	while(c.moveToNext()){
    		Map<String, Object> map=new HashMap<String, Object>();
    		map.put("title",c.getString(c.getColumnIndex("title")));
    		map.put("size",c.getString(c.getColumnIndex("size")));
    		map.put("path", c.getString(c.getColumnIndex("path")));
    		map.put("progress",c.getInt(c.getColumnIndex("progress")));
    		lists.add(map);
    	}
    	c.close();
    	return lists;
    }
	public List<Map<String, Object>> findall(){
    	Cursor c=db.query("shiping_downloading", new String[]{"_id","title","size","path","progress","kaiguan"},null,null,null,null,null);
    	List<Map<String, Object>> lists=new ArrayList<Map<String, Object>>();
    	while(c.moveToNext()){
    		Map<String, Object> map=new HashMap<String, Object>();
    		map.put("title",c.getString(c.getColumnIndex("title")));
    		map.put("size",c.getString(c.getColumnIndex("size")));
    		map.put("path", c.getString(c.getColumnIndex("path")));
    		map.put("progress",c.getInt(c.getColumnIndex("progress")));
    		map.put("kaiguan",c.getInt(c.getColumnIndex("kaiguan")));
    		lists.add(map);
    	}
    	c.close();
    	return lists;
    }
	public void FuWei(){
    	Cursor c=db.query("shiping_downloading", new String[]{"_id","title","size","path","progress","kaiguan"},null,null,null,null,null);
    	List<Map<String, Object>> lists=new ArrayList<Map<String, Object>>();
    	while(c.moveToNext()){
    		  db.execSQL("update shiping_downloading set kaiguan=? where path =?",new Object[]{"0",c.getString(c.getColumnIndex("path"))});
    	}
    	c.close();
    }
	public Cursor find4Listloading(){
    	Cursor c=db.query(SQLhelper.shiping_downloading, new String[]{"_id","title","size","path","progress"},null,null,null,null,null);
        return c;
    }
	 public Cursor findOnedownloading(int _id){
	    	Cursor c=db.rawQuery("select * from "+SQLhelper.shiping_downloading+" where _id = ?",new String[]{_id+""});
	    	c.close();
	        return c;
	    }
	public Cursor find4Listloaded(){
    	Cursor c=db.query(SQLhelper.shiping_downloaded, new String[]{"title","size"},null,null,null,null,null);
    	c.close();
        return c;
    }
	
	public void insertDownloading(String title,String size,String path,int progress,String table){
		db.execSQL("insert into "+table+" (title,size,path,progress) values (?,?,?,?);",new Object[]{title,size,path,progress});
	}
	
	public void drop(){
		db.delete(SQLhelper.shiping_downloading, null, null);
	}
	
	public void insertDownloaded(String title,String size,String path,int progress,String table){
		db.execSQL("insert into "+table+" (title,size,path,progress) values (?,?,?,?);",new Object[]{title,size,path,progress});
	}
	 public void deleteloading(String path,String tablename){
	    	db.execSQL("delete from "+tablename+"  where path=?;", new Object[]{path});
	 }
	
	 public boolean find4haveloading(String title,String table1,String table2){
	     int index=0;	
		 Cursor c=db.query(table1, new String[]{"_id","title","size","path","progress"},null,null,null,null,null);
	        while(c.moveToNext()){
	        	if(c.getString(c.getColumnIndex("title")).equals(title)){
	        		index++;
	        		break;
	        	}
	        }
	        c.close();
        	 Cursor  c1=db.query(table2, new String[]{"_id","title","size","path","progress"},null,null,null,null,null);
	        while(c1.moveToNext()){
	        	if(c1.getString(c1.getColumnIndex("title")).equals(title)){
	        		index++;
	        		break;
	        	}
	        } 
	        c1.close();
	        if(index>=1){
	        	return true;
	        }
	        
	    	return false;
	    }
	public void updateDownloading(String path,String size){
		   db.execSQL("update shiping_downloading set size=? where path =?",new Object[]{size,path});
	}
	public void updateDownloadingLaiguan(String path,String kaiguan){
		   db.execSQL("update shiping_downloading set kaiguan=? where path =?",new Object[]{kaiguan,path});
	}
	
	
	
	
    public void close(){
    	db.close();
    }
	
}
