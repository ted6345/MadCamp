package com.beginagain.hyclub00.adapter;

import java.util.List;

import com.beginagain.hyclub00.R;
import com.beginagain.hyclub00.data.ClubData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DataAdapter extends ArrayAdapter<ClubData>{
	
	private LayoutInflater inflater;
	private TextView name, cate;
	private ImageView logoView;
	private Context baseContext;
	
	public DataAdapter(Context context, Context baseContext, List<ClubData> dataArray) {
		super(context, 0, dataArray);
		
		this.baseContext = baseContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View v, ViewGroup parent) {
		View view = null;
		
		if(v == null){
			view = inflater.inflate(R.layout.custom_view, null); 
		} else {
			view = v;
		} 
		
		final ClubData data = this.getItem(position);
		
		if(data != null){
			name = (TextView)view.findViewById(R.id.Name);
			cate = (TextView)view.findViewById(R.id.Category);
			logoView = (ImageView)view.findViewById(R.id.LogoView);
			
			
			String category = data.getCate().replaceAll("전체/", "");
			String nCategory = category.replaceAll("/", ", ");
			
			name.setText(data.getName());
			cate.setText(nCategory);
			
			if(!data.getLogo().equals("logo_null")){
				String resName = "@drawable/" + data.getLogo();
				String packName = baseContext.getPackageName(); // 패키지명
				int resID = baseContext.getResources().getIdentifier(resName, "drawable", packName);
				logoView.setImageResource(resID);
			}
			else if(!data.getPost().equals("post_null")){
				String resName = "@drawable/" + data.getPost();
				String packName = baseContext.getPackageName();
				int resID = baseContext.getResources().getIdentifier(resName, "drawable", packName);
				logoView.setImageResource(resID);
			} else {
				logoView.setImageResource(R.drawable.logo_null);
			}
		}
		
		return view;
	}
}
