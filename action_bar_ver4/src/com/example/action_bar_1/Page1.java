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
				
				((MainActivity)getActivity()).setPagerCurrentPage(1);
			}
		});
		
//		
//	
//		ArrayList<ArrayList<HashMap<String, String>>> list_of_oslist = 
//				((MainActivity)getActivity()).getlist_of_oslist();
//		
//		ListView list = (ListView) view.findViewById(R.id.list);
//		
//		for (int i = 0; i < list_of_oslist.size(); i++) {
//			oslist = list_of_oslist.get(i);
//			
//			ListAdapter adapter = new SimpleAdapter((MainActivity)getActivity(),
//					oslist, R.layout.list_v, new String[] { TAG_STA,
//							TAG_CAP, TAG_LAT, TAG_LON }, new int[] {
//							R.id.sta, R.id.cap, R.id.lat, R.id.lon });
//			
//			list.setAdapter(adapter);
//			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> parent,
//						View view, int position, long id) {
//					
//					((MainActivity)getActivity()).setMessage(oslist.get(+position).get("state"));
//					
//					Toast.makeText(
//							(MainActivity)getActivity(),
//							"You Clicked at "
//									+ oslist.get(+position)
//											.get("state"),
//							Toast.LENGTH_SHORT).show();
//				}
//			});
//			
//			
//		}	
		return view;
	}

}