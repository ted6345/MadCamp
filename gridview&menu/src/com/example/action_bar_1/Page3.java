package com.example.action_bar_1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Page3 extends Fragment {
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.page3, container, false);
		((TextView) view.findViewById(R.id.textView3)).setText(((MainActivity)getActivity()).getSelectedFood());
		return view;
	}
}