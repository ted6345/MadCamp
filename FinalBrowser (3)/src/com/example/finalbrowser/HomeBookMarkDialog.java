package com.example.finalbrowser;

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

public class HomeBookMarkDialog extends DialogFragment {
	EditText edtname, edturl;
	ImageButton Addbtn;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder homedialog = new AlertDialog.Builder(getActivity());
	
		homedialog.setTitle("Homepage Setting");
		LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.home_dialog, null);
        homedialog.setView(view);
//    	edtname = (EditText) view.findViewById(R.id.edtname_home);
    	edturl = (EditText) view.findViewById(R.id.edturl_home);
//		edtname.setText(((MainActivity) getActivity()).getWeb().getTitle());
    	edturl.setText(((MainActivity) getActivity()).getWeb().getUrl());
		homedialog
				.setPositiveButton("Add",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								SharedPreferences pref = ((MainActivity) getActivity())
										.getPreferences(0);
								SharedPreferences.Editor editor = pref.edit();
								if (((MainActivity) getActivity()).getedtUrl()
										.length() > 3) {
									editor.putString("homepage",
											((MainActivity) getActivity())
													.getedtUrl());
									editor.commit();
									dismiss();
									Toast.makeText(
											((MainActivity) getActivity()),
											""
													+ ((MainActivity) getActivity())
															.getedtUrl(),
											Toast.LENGTH_SHORT).show();
								} else
									Toast.makeText(
											((MainActivity) getActivity()),
											"Invalid Url", Toast.LENGTH_SHORT)
											.show();
							}
						})

				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dismiss();
							}
						}).create();
		return homedialog.create();
	}
}
