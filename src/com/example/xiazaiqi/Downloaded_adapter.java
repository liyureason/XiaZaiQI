package com.example.xiazaiqi;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.example.sqlite.Dao;
import com.example.sqlite.SQLhelper;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.AlteredCharSequence;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Downloaded_adapter extends BaseAdapter {
	private Context context;
	List<Map<String, Object>> lists;
	private LayoutInflater inflater;

	public Downloaded_adapter(Context context, List<Map<String, Object>> lists) {
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
			convertView = inflater.inflate(R.layout.downloaded_listview_item, null);
			vh.tv1 = (TextView) convertView
					.findViewById(R.id.downloaded_listview_item_textView1);
			vh.tv2 = (TextView) convertView
					.findViewById(R.id.downloaded_listview_item_textView2);
			vh.btn1 = (Button) convertView
					.findViewById(R.id.downloaded_listview_item_button);
//			vh.btn2 = (Button) convertView
//					.findViewById(R.id.downloaded_listview_item_button2);
			convertView.setTag(vh);
		}
		vh = (ViewHolder) convertView.getTag();
		vh.tv1.setText(lists.get(position).get("title").toString());
		vh.tv2.setText(lists.get(position).get("size").toString());
		final Button button1 = vh.btn1;
//		final Button button2 = vh.btn2;
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				  AlertDialog.Builder builder = new Builder(context);
				  builder.setMessage("您确定要删除吗");

				  builder.setTitle("提示");

				  builder.setPositiveButton("确认", new  DialogInterface.OnClickListener() {

					  public void onClick(DialogInterface dialog, int which) {
						     String path=lists.get(position).get("path").toString();
						     File file=new File(path);
						     file.delete();
						     Dao d=new Dao(context);
						     d.deleteloading(path, SQLhelper.shiping_downloaded);
						     button1.setText("已删除");
						     button1.setEnabled(false);
						     Downloaded_adapter adapter=new Downloaded_adapter(context, d.findall(SQLhelper.shiping_downloaded));
							 Shiping_downloaded.listView.setAdapter(adapter);
//						     button2.setEnabled(false);
							 d.close();
					}
				});

				  builder.setNegativeButton("取消", null);

				  builder.create().show();


			}
		});
//		button2.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String url = lists.get(position).get("path").toString();
//				Intent intent = new Intent(Intent.ACTION_VIEW);    //创建一个intent,
//                intent.setType("video/*");    //設置其type，这里使用*支持全部格式
//                intent.setDataAndType(Uri.parse(url), "video/*");   //设置播放路径，可以是本地地址，也可以是网络地址
//                context.startActivity(intent);    //发送intent，启动播放器 				
//			}
//		});

		return convertView;
	}


	class ViewHolder {
		TextView tv1, tv2;
		Button btn1,btn2;

		public ViewHolder(TextView tv1, TextView tv2, Button btn1,Button btn2
				) {
			super();
			this.tv1 = tv1;
			this.tv2 = tv2;
			this.btn1 = btn1;
			this.btn2=btn2;
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

	}

}
