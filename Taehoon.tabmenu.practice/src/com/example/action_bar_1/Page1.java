package com.example.action_bar_1;

import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import android.os.Bundle;

import com.example.action_bar_1.MainActivity;
import com.example.action_bar_1.R;

public class Page1 extends Fragment {
	
	private static final String TAG_CIT = "citys";
	private static final String TAG_STA = "state";
	private static final String TAG_CAP = "capital";
	private static final String TAG_LAT = "latitude";
	private static final String TAG_LON = "longitude";
	ArrayList<HashMap<String, String>> oslist;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View view = inflater.inflate(R.layout.page1, container, false);
		
		
		final ArrayList<String> categories = ((MainActivity)getActivity()).getCategories();
		Collections.sort(categories.subList(1, categories.size()));
		
		ArrayAdapter<String> Adapter;
		Adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_single_choice, categories);
		
		ListView list = (ListView)view.findViewById(R.id.list);
		list.setAdapter(Adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				
				((MainActivity)getActivity()).setSelectedCategory(categories.get(+position));
				
				Toast.makeText(
						(MainActivity)getActivity(),
						"You Clicked at "
								+ categories.get(+position),
						Toast.LENGTH_SHORT).show();
			}
		});
		
	
		return view;
	}

}