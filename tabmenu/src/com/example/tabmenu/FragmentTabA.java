package com.example.tabmenu;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class FragmentTabA extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab_a, null);

		ArrayList<String> numbers = new ArrayList<String>();
		numbers.add("5");
		numbers.add("5");
		numbers.add("5");
		numbers.add("5");
		numbers.add("5");
		numbers.add("15");
		numbers.add("16");
		numbers.add("17");
		numbers.add("18");
		numbers.add("19");
		numbers.add("20");
		numbers.add("21");

		ArrayAdapter<String> Adapter;
		Adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, numbers);

		ListView list = (ListView) view.findViewById(R.id.list);
		list.setAdapter(Adapter);

		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		list.setDivider(new ColorDrawable(0x0A000000));
		list.setDividerHeight(2);

		return view;
	}
	 


}
