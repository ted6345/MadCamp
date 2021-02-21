package com.example.finalbrowser.normal;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.finalbrowser.MainActivity;
import com.example.finalbrowser.R;

public class History extends ListActivity{
	public static ArrayList<Site> list = new ArrayList<Site>();
    private ArrayAdapter<String> adapter;
	private WebView web;
	private ListView lv;
	public static Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		mContext = this;
		
		web = ((MainActivity) MainActivity.mContext).getWeb();
		lv = (ListView) findViewById(android.R.id.list);
		
		final HistoryAdapter m_adapter = new HistoryAdapter(this, R.layout.one_history, list); 
		// 어댑터를 생성합니다.
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = m_adapter.getItem(position).url;
				web.loadUrl(url);
				((History)mContext).finish();
			}
			
		});
		setListAdapter(m_adapter); 
	
		
		
	}
	
	public static void addOneHistory(String title,String url){
		if(list.size()>29)
			list.remove(29);
		for(Site c : list){
			if(c.title.startsWith(title)){
				return;
			}
		}
		list.add(0, new Site(title,url));
		
	}
}

