package com.example.finalbrowser;

import java.util.ArrayList;
import java.util.List;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddBookMarkDialog extends DialogFragment {
	List<String> bookmark = new ArrayList<String>();
	List<String> bookmark_name = new ArrayList<String>();
	String[] listbookmark;
	EditText edtname, edturl;
	ImageButton Addbtn;

	// Context mContext;

	public AddBookMarkDialog() {
		// mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.add_dialog, container);
		getDialog().setTitle("BOOKMARK Ãß°¡");
		edtname = (EditText) view.findViewById(R.id.edtname);
		edturl = (EditText) view.findViewById(R.id.edturl);
		Addbtn = (ImageButton) view.findViewById(R.id.addbutton);
		return view;
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		loadArray();
		edtname.setText(((MainActivity) getActivity()).getWeb().getTitle());
		edturl.setText(((MainActivity) getActivity()).getWeb().getUrl());
		Addbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				boolean overwrap = true;
				for (int i = 0; i < bookmark.size(); i++) {
					if (edturl.getText().toString().equals(bookmark.get(i)))
						overwrap = false;
					else if (edtname.getText().toString()
							.equals(bookmark_name.get(i)))
						overwrap = false;
				}
				if (overwrap && edturl.getText().toString().length() > 3) {
					bookmark.add(edturl.getText().toString());
					bookmark_name.add(edtname.getText().toString());
					saveArray();
					loadArray();
					dismiss();
					Toast.makeText(getActivity(), "Add Complete",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "Invalid Add",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void saveArray() {
		SharedPreferences pref = getActivity().getPreferences(0);
		SharedPreferences.Editor mEdit1 = pref.edit();
		mEdit1.putInt("Bookmark_size", bookmark.size());
		for (int i = 0; i < bookmark.size(); i++) {
			mEdit1.remove("Bookmark_" + i);
			mEdit1.putString("Bookmark_" + i, bookmark.get(i));
		}
		for (int i = 0; i < bookmark.size(); i++) {
			mEdit1.remove("Bookmark_name" + i);
			mEdit1.putString("Bookmark_name" + i, bookmark_name.get(i));
		}
		mEdit1.commit();
	}

	public void loadArray() {
		SharedPreferences pref = getActivity().getPreferences(0);
		bookmark.clear();
		int size = pref.getInt("Bookmark_size", 0);
		Log.v("Debugging", "bookmarksize" + size);
		for (int i = 0; i < size; i++) {
			bookmark.add(pref.getString("Bookmark_" + i, null));
		}
		for (int i = 0; i < size; i++) {
			bookmark_name.add(pref.getString("Bookmark_name" + i, null));
		}
		// listbookmark = new String[size];
		// for(int i=0;i<size;i++)
		// {
		// listbookmark[i] = bookmark.get(i);
		// }
	}

}
