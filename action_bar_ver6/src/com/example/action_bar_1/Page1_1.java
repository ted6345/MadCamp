package com.example.action_bar_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Page1_1 extends Fragment {
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.page1_1, container, false);
		
		
		
		HashMap<String, HashMap<String, String>> cate_and_menu = ((MainActivity)getActivity()).getcate_and_menu();
		
		HashMap<String, String> selected = cate_and_menu.get(((MainActivity)getActivity()).getSelectedCategory());
		
		final HashMap<String, String> reversedselected = new HashMap<String, String>();
		for (String key : selected.keySet()){
			reversedselected.put(selected.get(key), key);
		}
		
		Set<String> key_cate = selected.keySet();
		final ArrayList<String> categories = new ArrayList<String>();

		for (String str : key_cate) {
			categories.add(str);
		}
		Collections.sort(categories.subList(1, categories.size()));

		ArrayAdapter<String> Adapter;
		Adapter = new ArrayAdapter<String>((MainActivity)getActivity(),
					android.R.layout.simple_list_item_single_choice, categories);
		
		
		
		ListView list = (ListView)view.findViewById(R.id.list1_2);
		list.setAdapter(Adapter);
		
		list.setBackgroundDrawable(new ColorDrawable(0xffefefef));
		
		
		String selectedfood = ((MainActivity)getActivity()).getSelectedFood();
		int pos = categories.indexOf(selectedfood);

		
		
		
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			private int save = -1;
			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {

				((MainActivity)getActivity()).setSelectedFood(categories.get(position));
				((MainActivity)getActivity()).setPagerCurrentPage(2);
				
			}
		});
		
		//-----------------------------------------------------------------------------
		//¿ÞÂÊ ¸®½ºÆ®
		
		final ArrayList<String> categories_1 = ((MainActivity)getActivity()).getCategories();
		Collections.sort(categories_1.subList(1, categories_1.size()));
		
		ArrayAdapter<String> Adapter_1;
		Adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, categories_1);
		
		ListView list_1 = (ListView)view.findViewById(R.id.list1_1);
		list_1.setAdapter(Adapter);

		list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				((MainActivity) getActivity()).setSelectedCategory(categories_1
						.get(+position));

				((MainActivity)getActivity()).TabAdapter.notifyDataSetChanged();

				
			}
		});
		


		return view;
	}
	
}