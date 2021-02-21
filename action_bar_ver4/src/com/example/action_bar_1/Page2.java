package com.example.action_bar_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.example.action_bar_1.MainActivity;
import com.example.action_bar_1.R;
import com.example.action_bar_1.Page2.ImageAdapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Page2 extends Fragment {
//	View view;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		view = inflater.inflate(R.layout.page2, container, false);
//		
//		HashMap<String, HashMap<String, String>> cate_and_menu = ((MainActivity)getActivity()).getcate_and_menu();
//		
//		HashMap<String, String> selected = cate_and_menu.get(((MainActivity)getActivity()).getSelectedCategory());
//		
//		
//		Set<String> key_cate = selected.keySet();
//		ArrayList<String> categories = new ArrayList<String>();
//
//		for (String str : key_cate) {
//			categories.add(selected.get(str));
//		}
//		Collections.sort(categories.subList(1, categories.size()));
//		
//		ArrayAdapter<String> Adapter;
//		Adapter = new ArrayAdapter<String>(getActivity(),
//					android.R.layout.simple_list_item_single_choice, categories);
//		
//		ListView list = (ListView)view.findViewById(R.id.list2);
//		list.setAdapter(Adapter);
//
//		return view;
//	}
		  
//		Integer[] images = {R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4, R.drawable.cat5, R.drawable.cat6,R.drawable.cat7,R.drawable.cat8,
//				R.drawable.cat9, R.drawable.cat10,R.drawable.cat11};
//		Integer[] images2 = {R.drawable.dog1, R.drawable.dog2, R.drawable.dog3, R.drawable.dog4, R.drawable.dog5, R.drawable.dog6,R.drawable.dog7,R.drawable.dog8,
//				R.drawable.dog9, R.drawable.dog10,R.drawable.dog11};	
//		Integer[] setImage = images;

		final ArrayList<String> imagelist = new ArrayList<String>(); 
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			HashMap<String, HashMap<String, String>> cate_and_menu = ((MainActivity)getActivity()).getcate_and_menu();
			
			HashMap<String, String> selected = cate_and_menu.get(((MainActivity)getActivity()).getSelectedCategory());
			
			final HashMap<String, String> reversedselected = new HashMap<String, String>();
			for (String key : selected.keySet()){
				reversedselected.put(selected.get(key), key);
			}
			
			Set<String> key_cate = selected.keySet();
			ArrayList<String> categories = new ArrayList<String>();
	
			for (String str : key_cate) {
				Log.v("key", str);
				Log.v("value", selected.get(str));
				categories.add(selected.get(str));
			}
			Collections.sort(categories.subList(1, categories.size()));
			
			final View view = inflater.inflate(R.layout.page2, container, false);
			
			
			for(String str : categories) {
				imagelist.add(str);
			}
			
			
			
			Gallery gallery = (Gallery) view.findViewById(R.id.gallery1);
	        gallery.setAdapter(new ImageAdapter(getActivity()));        
			
	        
	       gallery.setOnItemClickListener(new OnItemClickListener(){
	       	@Override        
	       	public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
	       	   	

	       		
	       		ImageView imageView = (ImageView) view
	       				.findViewById(R.id.imageView1);
	       		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	       		String resName = "@drawable/" + imagelist.get(position);
	       		String packName = getActivity().getBaseContext().getPackageName();
	       		int resid = getActivity().getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
	       		imageView.setImageResource(resid);
	       		
	       		TextView text = ((TextView) view.findViewById(R.id.textView2));
	       		text.setGravity(Gravity.CENTER_HORIZONTAL);
	       		text.setText(reversedselected.get(imagelist.get(position)));
	       		
	       		
//	       	String resName = "@drawable/" + manseList.get(position).getName() + "_origin";
//	          String packName = getActivity().getBaseContext().getPackageName();
//	          int resId = getActivity().getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
//	          manseImage.setImageResource(resId);
	       		
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
	   		return imagelist.size();
	   	}
	   	public Integer getItem(int position)
	   	{
	   		String resName = "@drawable/" +imagelist.get(position);
	   		String packName = getActivity().getBaseContext().getPackageName();
	   		int resid = getActivity().getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
	   		
	   		return resid;
	   	}
	   	public Integer getItemid(int position)
	   	{
	   		return position;
	   	}
	   	public View getView(int position, View convertView, ViewGroup parent)
	   	{
	   		ImageView imageView= new ImageView(context);
	   		imageView.setImageResource(getItem(position));
	   		imageView.setLayoutParams(new Gallery.LayoutParams(350,260));
	   		imageView.setBackgroundResource(itemBackground);
	   		return imageView;
	   	}
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
	   }
	}

