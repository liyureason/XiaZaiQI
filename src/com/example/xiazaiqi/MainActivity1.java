package com.example.xiazaiqi;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.example.service.DownloadService;
import com.example.sqlite.Dao;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    public void add(View v){
    	Intent intent=new Intent(MainActivity1.this,Shiping_download.class);
//    	Map<String, Object> map=new HashMap<String, Object>();
//    	map.put("name", "拍问视频");
//    	map.put("path","http://192.168.1.118:8080/server/a.mp4");
//    	map.put("saveDir", new Environment().getExternalStorageDirectory());
    	
    	Dao d=new Dao(getApplicationContext());
//    	d.drop();
    	String title="apk";
    	boolean isHave=d.find4haveloading(title, "shiping_downloading", "shiping_downloaded");
		if(!isHave){
		d.insertDownloading(title, "0",
				"http://game.hb10008.cn/download/WZ3GDZP.apk", 0,"shiping_downloading");
		d.insertDownloading("title1", "0",
				"http://192.168.0.101:8080/mp3/a1.mp3", 0,"shiping_downloading");
		d.insertDownloading("title2", "0",
				"http://192.168.0.101:8080/mp3/a2.mp3", 0,"shiping_downloading");
		d.insertDownloading("title3", "0",
				"http://192.168.0.101:8080/mp3/a3.mp3", 0,"shiping_downloading");
		d.insertDownloading("title4", "0",
				"http://192.168.0.101:8080/mp3/a4.mp3", 0,"shiping_downloading");
		d.insertDownloading("title5", "0",
				"http://192.168.0.101:8080/mp3/a5.mp3", 0,"shiping_downloading");
		d.insertDownloading("title6", "0",
				"http://192.168.0.101:8080/mp3/a6.mp3", 0,"shiping_downloading");
		}else{
			Toast.makeText(MainActivity1.this,title+ "已存在", Toast.LENGTH_SHORT).show();
		}
    	d.close();
    	startActivity(intent);
    	
    }
    public void closeDownload(){
   	 for(int i=0;i<DownloadService.tasks.size();i++){
   	 DownloadService.tasks.get(i).loader.exit();
   	 }
   	 DownloadService.isRun=false;
   	 Intent service=new Intent(this,DownloadService.class);
		 stopService(service);
    }
	 protected void dialog() { 
		 AlertDialog.Builder builder = new Builder(MainActivity1.this); 
		 builder.setMessage("确定要退出吗?"); 
		 builder.setTitle("提示"); 
		 builder.setPositiveButton("确认", 
		 new android.content.DialogInterface.OnClickListener() { 
		 public void onClick(DialogInterface dialog, int which) { 
		 dialog.dismiss(); 
		 closeDownload();
		 MainActivity1.this.finish(); 
		 } 
		 }); 
		 builder.setNegativeButton("取消", 
		 new android.content.DialogInterface.OnClickListener() { 
		 public void onClick(DialogInterface dialog, int which) { 
		 dialog.dismiss(); 
		 } 
		 }); 
		 builder.create().show(); 
		 } 

		 @Override 
		 public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
		 dialog(); 
		 Toast.makeText(MainActivity1.this, "back", Toast.LENGTH_SHORT).show();
		 return true; 
		 } else if(keyCode == KeyEvent.KEYCODE_MENU) {
		 // rl.setVisibility(View.VISIBLE);
		 Toast.makeText(MainActivity1.this, "Menu", Toast.LENGTH_SHORT).show();
		 return false;
		 } else if(keyCode == KeyEvent.KEYCODE_HOME) {
		 //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
		 Toast.makeText(MainActivity1.this, "Home", Toast.LENGTH_SHORT).show();
		 return false;
		 }
		 return super.onKeyDown(keyCode, event);
		 }
//		 // 拦截/屏蔽系统Home键
//		 public void onAttachedToWindow() { 
//		 this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD); 
//		 super.onAttachedToWindow(); 
//		 }
}
