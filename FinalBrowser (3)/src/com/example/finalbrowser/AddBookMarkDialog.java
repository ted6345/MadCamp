package com.example.finalbrowser;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddBookMarkDialog extends DialogFragment {
	List<String> bookmark = new ArrayList<String>();
	List<String> bookmark_name = new ArrayList<String>();
	EditText edtname, edturl;
	ImageButton Addbtn;

	// Context mContext;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder adddialog = new AlertDialog.Builder(getActivity());
		adddialog.setTitle("Bookmark 추가");
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.add_dialog, null);
		adddialog.setView(view);
		edtname = (EditText) view.findViewById(R.id.edtname);
		edturl = (EditText) view.findViewById(R.id.edturl);
		edtname.setText(((MainActivity) getActivity()).getWeb().getTitle());
		edturl.setText(((MainActivity) getActivity()).getWeb().getUrl());
		adddialog
				.setPositiveButton("Add",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								boolean overwrap = true;
								for (int i = 0; i < bookmark.size(); i++) {
									if (edturl.getText().toString()
											.equals(bookmark.get(i)))
										overwrap = false;
									else if (edtname.getText().toString()
											.equals(bookmark_name.get(i)))
										overwrap = false;
								}
								if (overwrap
										&& edturl.getText().toString().length() > 3) {
									bookmark.add(edturl.getText().toString());
									bookmark_name.add(edtname.getText()
											.toString());
									saveArray();
									loadArray();
									dismiss();
									Toast.makeText(getActivity(),
											"Add Complete", Toast.LENGTH_SHORT)
											.show();
									((MainActivity) getActivity())
											.getBookmark();
								} else {
									Toast.makeText(getActivity(),
											"Invalid Add", Toast.LENGTH_SHORT)
											.show();
								}
							}
						})

				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dismiss();
							}
						}).create();
		return adddialog.create();
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
		for (int i = 0; i < size; i++) 
			bookmark.add(pref.getString("Bookmark_" + i, null));
		for (int i = 0; i < size; i++) 
			bookmark_name.add(pref.getString("Bookmark_name" + i, null));
	}

}
