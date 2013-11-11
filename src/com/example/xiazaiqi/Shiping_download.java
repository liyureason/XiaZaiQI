package com.example.xiazaiqi;




import com.example.service.DownloadService;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.TabActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;

public class Shiping_download extends TabActivity {
     TabHost tabhost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shiping_download);
		 tabhost=getTabHost();
	        Intent localIntent = new Intent();
			Intent remoteIntent = new Intent();
			localIntent.setClass(Shiping_download.this, Shiping_downloaded.class);
			remoteIntent.setClass(Shiping_download.this, Shiping_downloading.class);	
			TabHost.TabSpec remoteSpec = tabhost.newTabSpec("Remote");
			TabHost.TabSpec localSpec = tabhost.newTabSpec("Local");
			Resources res = getResources();
			remoteSpec.setIndicator("未下载",res.getDrawable(android.R.drawable.stat_sys_upload));
			remoteSpec.setContent(remoteIntent);	
			localSpec.setIndicator("已下载",res.getDrawable(android.R.drawable.stat_sys_download));
			localSpec.setContent(localIntent);
			tabhost.addTab(remoteSpec);
			tabhost.addTab(localSpec);
			tabhost.setCurrentTab(1);
	}
	
	

}
