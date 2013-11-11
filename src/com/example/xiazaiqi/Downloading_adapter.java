package com.example.xiazaiqi;

import java.util.List;
import java.util.Map;

import com.example.bean.ObjectUtil;
import com.example.bean.Task;
import com.example.service.DownloadService;
import com.example.sqlite.Dao;
import com.example.sqlite.SQLhelper;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Downloading_adapter extends BaseAdapter {
	private Context context;
	List<Map<String, Object>> lists;
	private LayoutInflater inflater;
	private boolean isClick;// 按下

	public Downloading_adapter(Context context, List<Map<String, Object>> lists) {
		this.context = context;
		this.lists = lists;
		inflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = new ViewHolder();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.downloading_activity, null);
			vh.tv1 = (TextView) convertView
					.findViewById(R.id.downloading_filename);
			vh.tv2 = (TextView) convertView
					.findViewById(R.id.downloading_percentpage);
			vh.bar = (ProgressBar) convertView
					.findViewById(R.id.downloading_progressBar);
			vh.btn1 = (Button) convertView
					.findViewById(R.id.downloading_startbutton);
			vh.btn2 = (Button) convertView
					.findViewById(R.id.downloading_stopbutton);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		vh.getTv1().setText(lists.get(position).get("title").toString());
		vh.getTv2().setText(lists.get(position).get("size").toString());
		vh.bar.setProgress(Integer.valueOf(lists.get(position).get("size")
				.toString()));
		final Button button1 = vh.btn1;
		final Button button2 = vh.btn2;
		if(lists.get(position).get("kaiguan").toString().equals("0")){
			button2.setEnabled(false);
			button1.setEnabled(true);
		}else if(lists.get(position).get("kaiguan").toString().equals("1")){
			button1.setEnabled(false);
			button2.setEnabled(true);
		}else{
			button1.setEnabled(false);
			button2.setEnabled(false);
		}
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				button1.setEnabled(false);
				button2.setEnabled(true);
					Task t = new Task();
					t.name = lists.get(position).get("path").toString();
					t.map.put("path", lists.get(position).get("path"));
					t.map.put("title", lists.get(position).get("title"));
					t.map.put("Button1", button1);
					t.map.put("Button2", button2);
					Dao d=new Dao(context);
					d.updateDownloadingLaiguan(lists.get(position).get("path").toString(),"1");
					d.close();
					DownloadService.queue.add(t);
				}
		});

		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				button2.setEnabled(false);
				button1.setEnabled(true);
				for (int i = 0; i < DownloadService.tasks.size(); i++) {
					if (DownloadService.tasks.get(i).name.equals(lists
							.get(position).get("path").toString())) {
						DownloadService.tasks.get(i).loader.exit();
						DownloadService.tasks.remove(i);
						break;
					}
				}
				Dao d=new Dao(context);
				d.updateDownloadingLaiguan(lists.get(position).get("path").toString(),"0");
				d.close();
				ObjectUtil.hasTask=false;
			}
		});
		return convertView;
	}

	public void newTask() {

	}

	class ViewHolder {
		TextView tv1, tv2;
		Button btn1, btn2;
		ProgressBar bar;

		public ViewHolder(TextView tv1, TextView tv2, Button btn1, Button btn2,
				ProgressBar bar) {
			super();
			this.tv1 = tv1;
			this.tv2 = tv2;
			this.btn1 = btn1;
			this.btn2 = btn2;
			this.bar = bar;
		}

		public ViewHolder() {
			super();
			// TODO Auto-generated constructor stub
		}

		public TextView getTv1() {
			return tv1;
		}

		public void setTv1(TextView tv1) {
			this.tv1 = tv1;
		}

		public TextView getTv2() {
			return tv2;
		}

		public void setTv2(TextView tv2) {
			this.tv2 = tv2;
		}

		public Button getBtn1() {
			return btn1;
		}

		public void setBtn1(Button btn1) {
			this.btn1 = btn1;
		}

		public Button getBtn2() {
			return btn2;
		}

		public void setBtn2(Button btn2) {
			this.btn2 = btn2;
		}

		public ProgressBar getBar() {
			return bar;
		}

		public void setBar(ProgressBar bar) {
			this.bar = bar;
		}

	}

}
