package com.example.finalbrowser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class TabManagerAdapter extends BaseAdapter{
	private ViewFlipper mViewFlipper;
	private LayoutInflater mInflater;
	private Context mContext;

	public TabManagerAdapter(Context context, ViewFlipper viewFlipper) {
		// 
		mContext = context;
		mViewFlipper = viewFlipper;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		System.out.println( mViewFlipper.getChildCount());
	}
	
	
	
	@Override
	public int getCount() {
		// 
		return mViewFlipper.getChildCount();
	}

	@Override
	public Object getItem(int position) {
		// 
		return 0;
	}

	@Override
	public long getItemId(int position) {
		// 
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		
		if(convertView == null){
			holder = new Holder();
			convertView = (LinearLayout) mInflater.inflate(R.layout.tab_manager_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.tab_title);
			holder.close = (ImageView) convertView.findViewById(R.id.tab_close_btn);
			holder.thumb = (ImageView) convertView.findViewById(R.id.tab_thumb);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		
		}
		
		CustomWebView web = (CustomWebView) mViewFlipper.getChildAt(position);
		holder.title.setText(web.getTitle());
		holder.thumb.setImageBitmap(web.getDrawingCache());
		holder.close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(position==0){
					CustomWebView web_temp = new CustomWebView(BroApplication.getInstance());
					mViewFlipper.removeAllViews();
					mViewFlipper.addView(web_temp);
					((TabManager) mContext).finish();
				}else{
					mViewFlipper.removeViewAt(position);
					notifyDataSetChanged();
				}
				
			}
		});
		
		/*holder.thumb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity)MainActivity.mContext).mhome = false;
				mViewFlipper.setDisplayedChild(position);
				((TabManager) mContext).setResult(Activity.RESULT_OK);
				((TabManager) mContext).finish();
			}
		});
		*/
		return convertView;
	}
	
	private class Holder {
		TextView title;
		ImageView close;
		ImageView thumb;
	}
}
