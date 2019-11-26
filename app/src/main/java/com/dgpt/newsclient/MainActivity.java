package com.dgpt.newsclient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dgpt.newsclient.bean.NewsInfo;
import com.dgpt.newsclient.utils.NewsInfoService;

public class MainActivity extends Activity {
	private ListView lv_news;
	private LinearLayout loading;
	private List<NewsInfo> newsInfos;
	//ListView适配器
	private class NewsAdapter extends BaseAdapter {
		//listView的item数
		public int getCount() {
			return newsInfos.size();
		}
		//得到listview 条目视图
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(MainActivity.this, R.layout.news_item,
					null);
			ImageView siv = (ImageView) view
					.findViewById(R.id.siv_icon);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			TextView tv_description = (TextView) view
					.findViewById(R.id.tv_description);
			TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
			NewsInfo newsInfo = newsInfos.get(position);
			//SmartImageView加载指定路径图片
			Glide.with(MainActivity.this).load(newsInfo.getIconPath()).into(siv);
			//设置新闻标题
			tv_title.setText(newsInfo.getTitle());
			//设置新闻描述
			tv_description.setText(newsInfo.getDescription());
			int type = newsInfo.getType(); // 1. 一般新闻 2.专题 3.live
			//不同新闻类型设置不同的颜色和不同的内容
			switch (type) {
				case 1:
					tv_type.setText("评论:" + newsInfo.getComment());
					break;
				case 2:
					tv_type.setTextColor(Color.RED);
					tv_type.setText("专题");
					break;
				case 3:
					tv_type.setTextColor(Color.BLUE);
					tv_type.setText("LIVE");
					break;
			}
			return view;
		}
		//条目对象
		public Object getItem(int position) {
			return null;
		}
		//条目id
		public long getItemId(int position) {
			return 0;
		}
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv_news = (ListView) findViewById(R.id.lv_news);
		loading = (LinearLayout) findViewById(R.id.loading);
		fillData2();
	}
	//使用AsyncHttpClient访问网络
	private void fillData2() {
		new Thread() {
			public void run() {
				// 连接服务器 get 请求 获取图片.
				try {
					URL url = new URL(MainActivity.this.getString(R.string.serverurl));       //创建URL对象
					// 根据url 发送 http的请求.
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					// 设置请求的方式
					conn.setRequestMethod("GET");
					//设置超时时间
					conn.setConnectTimeout(5000);
					// 得到服务器返回的响应码
					int code = conn.getResponseCode();
					//请求网络成功后返回码是200
					if (code == 200) {
						//获取输入流
						InputStream is = conn.getInputStream();
						//调用NewsInfoService工具类解析xml文件
						newsInfos = NewsInfoService.getNewsInfos(is);
						if (newsInfos == null) {
							// 解析失败 弹出toast
							Toast.makeText(MainActivity.this,
									"解析失败", Toast.LENGTH_SHORT).show();
						} else {
							// 更新界面.
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									loading.setVisibility(View.INVISIBLE);
									lv_news.setAdapter(new NewsAdapter());
								}
							});

						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();

	}
}

