package com.example.action_bar_1;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Page1 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View view = inflater.inflate(R.layout.page1, container, false);
		
		
		final ArrayList<String> categories = ((MainActivity)getActivity()).getCategories();
		Collections.sort(categories.subList(1, categories.size()));
		
		ArrayAdapter<String> Adapter;
		Adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, categories);
		
//		RadioButton rbChgPlan  = (RadioButton) view.findViewById(R.id.ckbox1);
//	    rbChgPlan.setChecked(true);
		
		ListView list = (ListView)view.findViewById(R.id.list);
		list.setAdapter(Adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				
				((MainActivity)getActivity()).setSelectedCategory(categories.get(+position));
				
			
				((MainActivity)getActivity()).setPagerCurrentPage(1);
			}
		});
		return view;
	}

}