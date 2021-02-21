package com.example.finalbrowser.normal;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.internal.widget.AdapterViewCompat.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.finalbrowser.R;

public class HistoryAdapter extends ArrayAdapter<Site> {
	private ArrayList<Site> items;

	public HistoryAdapter(Context context, int textViewResourceId,
			ArrayList<Site> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.one_history, null);
		}
		Site p = items.get(position);
		if (p != null) {
			TextView tt = (TextView) v.findViewById(R.id.toptext);
			TextView bt = (TextView) v.findViewById(R.id.bottomtext);
			if (tt != null) {
				tt.setText(p.title);
			}
			if (bt != null) {
				bt.setText(p.url);
			}
		}
		return v;
	}
	
}
