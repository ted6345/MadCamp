package com.example.action_bar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Page3 extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page3, container, false);
		Button dogbutton = (Button) view.findViewById(R.id.dogbutton);
		dogbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Toast.makeText(getActivity(), "You Clicked at DogGallery",
						Toast.LENGTH_SHORT).show();
				((MainActivity) getActivity()).setPageTag(1);

			}
		});

		Button catbutton = (Button) view.findViewById(R.id.catbutton);

		catbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Toast.makeText(getActivity(), "You Clicked at CatGallery",
						Toast.LENGTH_SHORT).show();

				((MainActivity) getActivity()).setPageTag(0);

			}
		});

		return view;
	}
}