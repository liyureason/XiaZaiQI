package com.example.xiazaiqi;



import java.util.List;
import java.util.Map;
import com.example.service.DownloadService;
import com.example.sqlite.Dao;
import com.example.sqlite.SQLhelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

public class Shiping_downloading extends Activity{
    public static ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shiping_downloading);
		listView=(ListView)findViewById(R.id.shiping_downloading_list);
		getData();
		Intent intent =new Intent(this,DownloadService.class);
		startService(intent);
	}
	
	public void getData(){
		Dao d=new Dao(getApplicationContext());
		Cursor c=d.find4Listloading();
		List<Map<String, Object>> lists=d.findall();
		Downloading_adapter adapter=new Downloading_adapter(getApplicationContext(), lists);
		listView.setAdapter(adapter);
		c.close();
		d.close();
	}
	
}
