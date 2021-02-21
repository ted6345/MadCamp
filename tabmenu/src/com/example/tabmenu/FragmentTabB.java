package com.example.tabmenu;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class FragmentTabB extends Fragment {
//
	Integer[] images ={R.drawable.cat1,R.drawable.cat2,R.drawable.cat3,R.drawable.cat4,R.drawable.cat5,R.drawable.cat6,R.drawable.cat7,R.drawable.cat8,
			R.drawable.cat9, R.drawable.cat10,R.drawable.cat11};
//	
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_tab_b, container, false);
		
		Gallery gallery = (Gallery) view.findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(getActivity()));        
		
        
        gallery.setOnItemClickListener(new OnItemClickListener(){
        	@Override        
        	public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
        	ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);    
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        	imageView.setImageResource(images[position]); 
		}
		});
	
		return view;
	}
	
    public class ImageAdapter extends BaseAdapter{
       	private int itemBackground;
    	private Context context;
    	public ImageAdapter(Context c)
    	{
    		context=c;
    		TypedArray a= getActivity().obtainStyledAttributes(R.styleable.gallery1);
    		itemBackground = a.getResourceId(R.styleable.gallery1_android_galleryItemBackground, 0);
    		a.recycle();
    	}	
    	public int getCount(){
    		return images.length;
    	}
    	public Integer getItem(int position)
    	{
    		return images[position];
    	}
    	public Integer getItemid(int position)
    	{
    		return position;
    	}
    	public View getView(int position, View convertView, ViewGroup parent)
    	{
    		ImageView imageView= new ImageView(context);
    		imageView.setImageResource(getItem(position));
    		imageView.setLayoutParams(new Gallery.LayoutParams(400,200));
    		imageView.setBackgroundResource(itemBackground);
    		return imageView;
    	}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

    }
	
	
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//    
//    
//
}




//
//public class FragmentTabB extends Fragment {
//
//	Integer[] images ={R.drawable.cat1,R.drawable.cat2,R.drawable.cat3,R.drawable.cat4,R.drawable.cat5,R.drawable.cat6,R.drawable.cat7,R.drawable.cat8,
//			R.drawable.cat9, R.drawable.cat10,R.drawable.cat11};
//	 @Override
//	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			 Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.fragment_tab_b, null);
//	 	return R.layout.fragment_tab_b;
//	 }	