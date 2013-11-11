package com.example.service;


import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.example.bean.ObjectUtil;
import com.example.bean.Task;
import com.example.sqlite.Dao;
import com.example.xiazaiqi.Downloading_adapter;
import com.example.xiazaiqi.R;
import com.example.xiazaiqi.Shiping_download;
import com.example.xiazaiqi.Shiping_downloading;



import cn.itcast.net.download.DownloadProgressListener;
import cn.itcast.net.download.FileDownloader;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadService extends Service{
    /**主线程开关*/
	public static  Boolean isRun;
	/**队列集合*/
	public static Queue<Task> queue=new LinkedList<Task>();
	/**提示栏提示的消息*/
	private String resultMessage;
	
	public static List<Task> tasks=new ArrayList<Task>();
	/**文件下载的位置*/
	public final String sdpath="mnt/sdcard/PaiWenShiPing";
	/**单个文件下载的线程数*/
	public final int threadNum=3;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		isRun=true;
		new download().start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	class download extends Thread{

		@Override
		public void run() {
			super.run();
			while(isRun){
			if(!queue.isEmpty()&&!ObjectUtil.hasTask){
				Task t=queue.poll();
				doTask(t);
				ObjectUtil.hasTask=true;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
		}

		private void doTask(Task t) {
			String path=t.map.get("path").toString();
//			File saveDir=Environment.getExternalStorageDirectory();
			File saveDir=new File(sdpath);
			if(!saveDir.isDirectory()){saveDir.mkdir();}
//			int position= (Integer) t.map.get("position");
		    DownloadTask  load=new DownloadTask(path, saveDir,t,0);
			new Thread(load).start();
		}
		
	}
	
	public IBinder onBind(Intent intent) {
		return null;
	}

	
	private final class DownloadTask implements Runnable{
		private String path;
		private File saveDir;
	    FileDownloader loader;
		private Task t;
		private int position;
		public DownloadTask(String path, File saveDir,Task t,int position) {
			this.path = path;
			this.saveDir = saveDir;
			this.t=t;
			this.position=position;
		}
		/**
		 * �˳�����
		 */
		public void exit(){
			if(loader!=null) loader.exit();
		}
		
		public synchronized void run() {
			try {
				loader = new FileDownloader(getApplicationContext(), path, saveDir,threadNum ,position);
//				((ProgressBar) t.map.get("bar")).setMax(loader.getFileSize());//���ý���������̶�
				t.loader=this.loader;
				final int filesize=loader.getFileSize();
				tasks.add(t);
				loader.download(new DownloadProgressListener() {
					public void onDownloadSize(int size) {
						Message msg = new Message();
						msg.what = 1;
						Bundle b=new Bundle();
						size=(int)((long)size*100/filesize);
						b.putInt("size", size);
					    msg.obj=t;
					    b.putCharSequence("file",loader.saveFile.getAbsolutePath());
					    msg.setData(b);
						handler.sendMessage(msg);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				Message msg = new Message();
				msg.what = -1;
			    msg.obj=t;
				handler.sendMessage(msg);
			}
		}			
	}
	
	  private Handler handler = new UIHander();
	    
	    private  class UIHander extends Handler{
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Bundle b=msg.getData();
					int size =b.getInt("size");
					Task t=(Task) msg.obj;
					Dao d=new Dao(getApplicationContext());
					d.updateDownloading(t.map.get("path").toString(), size+"");
						Cursor c=d.find4Listloading();
						List<Map<String, Object>> lists=d.findall();
						Downloading_adapter adapter=new Downloading_adapter(getApplicationContext(), lists);
						Shiping_downloading.listView.setAdapter(adapter);
					
					if(size==100){
						Toast.makeText(getApplicationContext(), "成功下载", Toast.LENGTH_SHORT).show();
//						showNotification(true);
						Button button=(Button) t.map.get("Button2");
						button.setEnabled(false);
						String path=b.getString("file");
						d.insertDownloaded(t.map.get("title").toString(),100+"字节", path, 100,"shiping_downloaded");
						d.updateDownloadingLaiguan(t.map.get("path").toString(), "2");
						d.deleteloading(t.map.get("path").toString(),"shiping_downloading");
						d.updateDownloading(t.map.get("path").toString(), size+"");
						 c=d.find4Listloading();
						 lists=d.findall();
						 adapter=new Downloading_adapter(getApplicationContext(), lists);
						Shiping_downloading.listView.setAdapter(adapter);
						c.close();
						ObjectUtil.hasTask=false;
					}
					d.close();
					break;
				case -1:
					Toast.makeText(getApplicationContext(), "下载失败，请重按开始按钮",  Toast.LENGTH_SHORT).show();
					Task t1=(Task) msg.obj;
					Button button2=(Button) t1.map.get("Button2");
					button2.setEnabled(false);
					Button button1=(Button) t1.map.get("Button1");
					button1.setEnabled(true);
					ObjectUtil.hasTask=false;
					break;
				}
			}
	    }
	    public void showNotification(boolean flag) {
			NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			Intent i = new Intent(DownloadService.this,
					Shiping_download.class);
			PendingIntent pi = PendingIntent.getActivity(DownloadService.this,
					0, i, PendingIntent.FLAG_UPDATE_CURRENT);
			Notification n;
			if (flag) {
				n = new Notification(R.drawable.ic_launcher, "下载成功",
						System.currentTimeMillis());
				n.setLatestEventInfo(DownloadService.this, resultMessage,
						"下载成功！！", pi);
				n.flags=Notification.FLAG_AUTO_CANCEL;
			} else {
				n = new Notification(R.drawable.ic_launcher, "正在下载",
						System.currentTimeMillis());
				n.setLatestEventInfo(DownloadService.this, resultMessage,
						"正在下载", pi);
			}
			nm.notify(R.layout.downloading_activity, n);
			Log.i("tag", "---------------->" + resultMessage);
		}
//	
//	    
	    
	    
		@Override
		public void onDestroy() {
			isRun=false;
			Dao d=new Dao(getApplicationContext());
			d.FuWei();
			d.close();
			super.onDestroy();
		}
	    
	    
}
