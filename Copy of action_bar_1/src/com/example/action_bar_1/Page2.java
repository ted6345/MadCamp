package com.example.action_bar_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import com.example.action_bar_1.MainActivity;
import com.example.action_bar_1.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Page2 extends Fragment {

	final ArrayList<String> imagelist = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		HashMap<String, HashMap<String, String>> cate_and_menu = ((MainActivity) getActivity())
				.getcate_and_menu();
		HashMap<String, String> selected = cate_and_menu
				.get(((MainActivity) getActivity()).getSelectedCategory());

		Set<String> key_cate = selected.keySet();
		
		ArrayList<String> categories = new ArrayList<String>();
				
		for (String str : key_cate) {
			categories.add(selected.get(str));
		}
		Collections.sort(categories.subList(1, categories.size()));

		final View view = inflater.inflate(R.layout.page2, container, false);

		for (String str : categories) {
			imagelist.add(str);
		}

		final Gallery gallery = (Gallery) view.findViewById(R.id.gallery1);
		final ImageAdapter adapter = new ImageAdapter(getActivity());
		gallery.setAdapter(adapter);
		final ImageButton deletebutton = (ImageButton) view.findViewById(R.id.deletebutton);
		final ImageView imageView = (ImageView) view
				.findViewById(R.id.imageView1);
		
		
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view1,
					final int position, long id) {

				
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				String resName = "@drawable/" + imagelist.get(position);
				String packName = getActivity().getBaseContext()
						.getPackageName();
				int resid = getActivity().getBaseContext().getResources()
						.getIdentifier(resName, "drawable", packName);
				imageView.setImageResource(resid);
			
				deletebutton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Toast.makeText(getActivity(), "delete the image",
								Toast.LENGTH_SHORT).show();
						
						imagelist.remove(position);
						adapter.notifyDataSetChanged();
						gallery.setAdapter(adapter);
						imageView.setImageResource(0);
						
						
					}
				});			
				
			}
		});
		
		imageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				imageView.setImageResource(0);
					}
		
		});
		
		ImageButton menubutton = (ImageButton) view.findViewById(R.id.menubutton);
		menubutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getActivity(), "매뉴가 뜬다 치고",
						Toast.LENGTH_SHORT).show();
				
			}
		
		});
	
		return view;
	}

	public class ImageAdapter extends BaseAdapter {

		private int itemBackground;
		private Context context;

		public ImageAdapter(Context c) {
			context = c;
			TypedArray a = getActivity().obtainStyledAttributes(
					R.styleable.gallery1);
			itemBackground = a.getResourceId(
					R.styleable.gallery1_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			return imagelist.size();
		}

		public Integer getItem(int position) {
			String resName = "@drawable/" + imagelist.get(position);
			String packName = getActivity().getBaseContext().getPackageName();
			int resid = getActivity().getBaseContext().getResources()
					.getIdentifier(resName, "drawable", packName);

			return resid;
		}

		public Integer getItemid(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(getItem(position));
			imageView.setLayoutParams(new Gallery.LayoutParams(250, 180));
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
