package com.example.action_bar_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class Page2 extends Fragment {

		final ArrayList<String> imagelist = new ArrayList<String>(); 
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
			
			// page1으로부터 데이터를 받아 string 배열로 변환
			HashMap<String, HashMap<String, String>> cate_and_menu = ((MainActivity)getActivity()).getcate_and_menu();			
			HashMap<String, String> selected = cate_and_menu.get(((MainActivity)getActivity()).getSelectedCategory());
		final HashMap<String, String> reversedselected = new HashMap<String, String>();
		for (String key : selected.keySet())
			reversedselected.put(selected.get(key), key);
		Set<String> key_cate = selected.keySet();
		ArrayList<String> categories = new ArrayList<String>();
		for (String str : key_cate)
			categories.add(str);
		Collections.sort(categories.subList(1, categories.size()));
		for (String str : categories)
			imagelist.add(selected.get(str));

		int tmpposition = ((MainActivity) getActivity()).gettmpposition();

		String tmpstring = ((MainActivity) getActivity()).gettmpstring();

		final String select = ((MainActivity) getActivity())
				.getSelectedCategory();

		final View view = inflater.inflate(R.layout.page2, container, false);

		GridView gallery = (GridView) view.findViewById(R.id.gallery);
	        ImageAdapter adapter = new ImageAdapter(getActivity());
			gallery.setAdapter(adapter);
			final ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
       		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
       		final TextView text = ((TextView) view.findViewById(R.id.textView2));
       		text.
Gravity(Gravity.CENTER_HORIZONTAL);
       		
       		
       		int selectedposition=((MainActivity)getActivity()).getSelectedPosition();
       		if(selectedposition!=0){
       			String resName = "@drawable/" + imagelist.get(selectedposition);
           		String packName = getActivity().getBaseContext().getPackageName();
           		int resid = getActivity().getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
           		imageView.setImageResource(resid);
           		text.setText(reversedselected.get(imagelist.get(selectedposition)));
     
       		}
       		else if(select==tmpstring){
       			String resName = "@drawable/" + imagelist.get(tmpposition);
           		String packName = getActivity().getBaseContext().getPackageName();
           		int resid = getActivity().getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
           		imageView.setImageResource(resid);
           		text.setText(reversedselected.get(imagelist.get(tmpposition)));
           	}
       		else {
    			String resName = "@drawable/" + imagelist.get(0);
           		String packName = getActivity().getBaseContext().getPackageName();
           		int resid = getActivity().getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
           		imageView.setImageResource(resid);
           		text.setText(reversedselected.get(imagelist.get(0)));
           	}
       		
       		
	        gallery.setOnItemClickListener(new OnItemClickListener(){
	       	@Override        
	       	public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
	       		String resName = "@drawable/" + imagelist.get(position);
	       		String packName = getActivity().getBaseContext().getPackageName();
	       		int resid = getActivity().getBaseContext().getResources().getIdentifier(resName, "drawable", packName);
	       		imageView.setImageResource(resid);
	       		imageView.setLayoutParams(new LayoutParams(533,258));
	       		text.setText(reversedselected.get(imagelist.get(position)));
	       		((MainActivity)getActivity()).settmpposition(position);
	       		((MainActivity)getActivity()).settmpstring(select);
	       		((MainActivity)getActivity()).setSelectedFood(reversedselected.get(imagelist.get(position)));
	       		((MainActivity)getActivity()).setSelectedPosition(position);
	       		
	       		imageView.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						//thisline
						((MainActivity)getActivity()).setPagerCurrentPage(3);
						//thisline
						
					}
	       			
	       			
	       		});
	    
	       	}
			});
		
			return view;
	}
		
	public class ImageAdapter extends BaseAdapter{
	    
	   	private Context context;
	   	public ImageAdapter(Context c)
	   	{
	   		context=c;
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
	   		imageView.setLayoutParams(new GridView.LayoutParams(200,164));
	   		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	   		return imageView;
	   	}
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
	   }
	}

