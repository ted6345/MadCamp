package com.example.finalbrowser.htmlview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalbrowser.MainActivity;
import com.example.finalbrowser.R;


public class HtmlModify extends Activity {
	EditText htmlmd;
	EditText etSearch;
	ImageView btSearch;
	ImageView btConfirm;
	public static Context mContext;
	String temp_head = "";
	String search_buffer = "";
	int start = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.htmlmodify);
		mContext = this;
		htmlmd = (EditText) findViewById(R.id.tv_htmlmd);
		etSearch = (EditText) findViewById(R.id.et_search);
		btSearch = (ImageView) findViewById(R.id.bt_search);
		btConfirm = (ImageView) findViewById(R.id.bt_confirm);
		// htmlmd.setMaxLines(100);
		// htmlmd.setVerticalScrollBarEnabled(true);
		// htmlmd.setMovementMethod(new ScrollingMovementMethod());
		System.out.println("디버그-2");
		String html_src;
		while((html_src= ((MainActivity) MainActivity.mContext).html).split("</head>").length<2){
			try {
				wait(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Html htmltest = null;
		temp_head = html_src.split("</head>")[0] + "</head>";
		System.out.println(html_src.split("</head>").length);
		html_src = html_src.split("</head>")[1];
		// html_src = html_src.split("</head>")[1].trim();
		// html_src = html_src.replaceAll("\n","").replaceAll(">", ">\n"); // 줄
		// 이쁘게 바꾸기
		// System.out.println(htmltest.fromHtml(html_src));
		System.out.println("디버그-1");
		String host_name = ((MainActivity) MainActivity.mContext).base_url;
		System.out.println("디버그0 : " + html_src.length());
		System.out.println("디버그1 : " + host_name);
		//html_src = html_src.replaceAll("src=\"/", "src=\"" + host_name + "/")
		//		.replaceAll("href=\"/", "href=\"" + host_name + "/")
		//		.replaceAll("url\\(/", "url\\(" + host_name + "/")
		//		.replaceAll("src='/", "src='" + host_name + "/")
		//		.replaceAll("href='/", "href='" + host_name + "/");
		System.out.println("디버그2");
		htmlmd.setText(html_src);
		
		btSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				search(etSearch.getText().toString());
			}
		});
		
		btConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) MainActivity.mContext).temp_repo = temp_head + htmlmd.getText()
						.toString();
				((HtmlModify)mContext).finish();
			}
		});
	}
	
	public void search(String word){
		if(htmlmd.getText().toString().contains(word)){
			if(!word.equals(search_buffer)){
				start = 0;
				search_buffer = word;
			}
			int index = htmlmd.getText().toString().indexOf(word,start);
			if(index<0){
				Toast.makeText(this, "더 이상 문자가 없습니다",Toast.LENGTH_SHORT).show();
				return;
			}
			start = index+word.length();
			htmlmd.requestFocus();
			htmlmd.setSelection(index, start);
		}
	}
}
