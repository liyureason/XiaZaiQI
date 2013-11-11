package com.example.xiazaiqi;

import com.example.sqlite.Dao;
import com.example.sqlite.SQLhelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Shiping_downloaded extends Activity{
    public static  ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shiping_downloaded);
		listView=(ListView) findViewById(R.id.shiping_downloaded_list);
		getData();
		
	}
	/**
	 * 从数据库得到数据
	 */
	
	public void getData(){
		Dao d=new Dao(this);
		Downloaded_adapter adapter=new Downloaded_adapter(this, d.findall(SQLhelper.shiping_downloaded));
        if(adapter!=null){
		listView.setAdapter(adapter);
        }
		d.close();
	}
	@Override
	protected void onResume() {
		super.onResume();
		getData();
	}
}
