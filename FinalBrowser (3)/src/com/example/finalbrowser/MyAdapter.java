package com.example.finalbrowser;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<String> {
	private ArrayList<String> items;
	private ArrayList<String> itemsAll;
	private ArrayList<String> suggestions;
	private int viewResourceId;

	@SuppressWarnings("unchecked")
	public MyAdapter(Context context, int resource, ArrayList<String> objects) {
		super(context, resource, objects);
		this.items = objects;
		this.itemsAll = (ArrayList<String>) objects.clone();
		this.suggestions = new ArrayList<String>();
		this.viewResourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = null;
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(viewResourceId, null);
		}

		// String product = items.get(position);
		// if (product != null) {
		// TextView productLabel = (TextView)
		// v.findViewById(android.R.id.text1);
		// if (productLabel != null) {
		// productLabel.setText(product.getProductName());
		// }
		// }
		textView = (TextView) v.findViewById(android.R.id.text1);
		String temp = items.get(position);
		if (temp != null) {
			if (textView != null)
				textView.setText(temp);
		}
		return v;
	}

	@Override
	public Filter getFilter() {
		return myFilter;
	}

	@SuppressLint("DefaultLocale")
	Filter myFilter = new Filter() {
		public CharSequence convertResultToString(Object resultValue) {
			return (CharSequence) resultValue;
		};

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			if (constraint != null) {
				suggestions.clear();
				for (String product : itemsAll) {
					if (product.toLowerCase().contains(
							constraint.toString().toLowerCase())) {
						suggestions.add(product);
					}
				}
				FilterResults filterResults = new FilterResults();
				filterResults.values = suggestions;
				filterResults.count = suggestions.size();
				return filterResults;
			} else {
				return new FilterResults();
			}
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			@SuppressWarnings("unchecked")
			ArrayList<String> filteredList = (ArrayList<String>) results.values;
			if (results != null && results.count > 0) {
				clear();
				for (String c : filteredList) {
					add(c);
				}
				notifyDataSetChanged();
			}
		}
	};
}
