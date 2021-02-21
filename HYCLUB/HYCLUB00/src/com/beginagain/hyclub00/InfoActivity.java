package com.beginagain.hyclub00;

import com.beginagain.hyclub00.data.ClubData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoActivity extends Activity {
	private TextView nameData, cateData, pnameData, numData, sizeData, locData, detailData, moreText,
						periodData, wayData, targetData, majorData, dayData, pageData, qData,
						plusData, careerData, intro;
	private ImageView logoData, picData, posterData;
	private LinearLayout moreView, periodView, wayView, targetView, majorView, dayView, pageView, qView,
						plusView, posterView, careerView;
	private String Q, category, packName;
	private int moreCdt = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_info);
		
		Intent info = getIntent();
		
		ClubData data = info.getParcelableExtra("clubInfo");
		
		packName = getBaseContext().getPackageName();
		
		nameData = (TextView)findViewById(R.id.NameData);
		cateData = (TextView)findViewById(R.id.CateData);
		pnameData = (TextView)findViewById(R.id.PnameData);
		numData = (TextView)findViewById(R.id.NumData);
		sizeData = (TextView)findViewById(R.id.SizeData);
		locData = (TextView)findViewById(R.id.LocData);
		detailData = (TextView)findViewById(R.id.DetailData);
		moreText = (TextView)findViewById(R.id.MoreText);
		periodData = (TextView)findViewById(R.id.PeriodData);
		wayData = (TextView)findViewById(R.id.WayData);
		targetData = (TextView)findViewById(R.id.TargetData);
		majorData = (TextView)findViewById(R.id.MajorData);
		dayData = (TextView)findViewById(R.id.DayData);
		pageData = (TextView)findViewById(R.id.PageData);
		qData = (TextView)findViewById(R.id.QData);
		plusData = (TextView)findViewById(R.id.PlusData);
		careerData = (TextView)findViewById(R.id.CareerData);
		intro = (TextView)findViewById(R.id.Intro);
		
		logoData = (ImageView)findViewById(R.id.logoData);
		picData = (ImageView)findViewById(R.id.PicData);
		posterData = (ImageView)findViewById(R.id.PosterData);
		
		moreView = (LinearLayout)findViewById(R.id.MoreView);
		periodView = (LinearLayout)findViewById(R.id.PeriodView);
		wayView = (LinearLayout)findViewById(R.id.WayView);
		targetView = (LinearLayout)findViewById(R.id.TargetView);
		majorView = (LinearLayout)findViewById(R.id.MajorView);
		dayView = (LinearLayout)findViewById(R.id.DayView);
		pageView = (LinearLayout)findViewById(R.id.PageView);
		qView = (LinearLayout)findViewById(R.id.QView);
		plusView = (LinearLayout)findViewById(R.id.PlusView);
		posterView = (LinearLayout)findViewById(R.id.PosterView);
		careerView = (LinearLayout)findViewById(R.id.CareerView);
		
		nameData.setText(data.getName());
		
		category = data.getCate().replace("전체/", "") + "\n" + data.getRange().replace("전체/", "") + "동아리";
		
		cateData.setText(category);
		
		pnameData.setText(data.getP_name());
		
		numData.setText(data.getNum());
		
		sizeData.setText(data.getSize());
		
		locData.setText(data.getLoc());
		
		if(!data.getLogo().equals("logo_null")){
			String resName = "@drawable/" + data.getLogo();
			int resID = getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
			logoData.setImageResource(resID);
		}
		else if(!data.getPost().equals("post_null")){
			String resName = "@drawable/" + data.getPost();
			int resID = getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
			logoData.setImageResource(resID);
		} else {
			logoData.setImageResource(R.drawable.logo_null);
		}
		
		if(!data.getPicture().equals("pic_null")){
			picData.setVisibility(View.VISIBLE);
			String resName = "@drawable/" + data.getPicture();
			int resId = getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
			picData.setImageResource(resId);
		} else {
			picData.setVisibility(View.GONE);
		}
		
		detailData.setText(data.getDetail().replace("___", "\n"));
		
		moreText.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(moreCdt == 0){
					moreView.setVisibility(View.VISIBLE);
					moreCdt = 1;
					moreText.setText(Html.fromHtml("<u>상세 정보 더보기 ▼</u>"));
				} else {
					moreView.setVisibility(View.GONE);
					moreCdt = 0;
					moreText.setText(Html.fromHtml("<u>상세 정보 더보기 ▶</u>"));
				}
			}
		});
		
		periodData.setText(data.getPeriod().replace("___", "\n"));
		
		wayData.setText(data.getWay().replace("___", "\n"));
		
		targetData.setText(data.getTarget().replace("___", "\n"));
		
		if(data.getRange().contains("과")&&!data.getTarget().contains("(모든 과에서 뽑습니다)")){
			majorView.setVisibility(View.VISIBLE);
			majorData.setText(data.getMajor().replace("전체/", ""));
		} else {
			majorView.setVisibility(View.GONE);
		}
		
		dayData.setText(data.getDay().replace("___", "\n"));
		
		pageData.setText(data.getPage().replace("___", "\n"));
		
		Q = data.getP_name() + "\n" + data.getP_phone();
		
		if(!data.getP_mail().equals(".")){
			Q = Q + "\n" + data.getP_mail();
		}
		
		qData.setText(Q.replace("___", "\n"));
		
		if(data.getPlus().equals(".")){
			plusView.setVisibility(View.GONE);
		} else {
			plusView.setVisibility(View.VISIBLE);
			plusData.setText(data.getPlus().replace("___", "\n"));
		}
		
		if(!data.getPost().equals("post_null") || !data.getCareer().equals(".")){
			posterView.setVisibility(View.VISIBLE);
			if(data.getName().equals("ICEWALL")){
				intro.setText("포스터 및 요구사항");
				TextView careerText = (TextView)findViewById(R.id.CareerText);
				careerText.setText("요구사항");
			}
			if(!data.getPost().equals("post_null")){
				posterData.setVisibility(View.VISIBLE);
				String resName = "@drawable/" + data.getPost();
				int resId = getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
				posterData.setImageResource(resId);
			} else 
				posterData.setVisibility(View.GONE);
			if(!data.getCareer().equals(".")){
				careerView.setVisibility(View.VISIBLE);
				careerData.setText(data.getCareer().replace("___", "\n"));
			} else 
				careerView.setVisibility(View.GONE);
		} else 
			posterView.setVisibility(View.GONE);
	}
}
