package com.beginagain.hyclub00;

import java.util.ArrayList;

import com.beginagain.hyclub00.adapter.DataAdapter;
import com.beginagain.hyclub00.adapter.MySQLiteOpenHelper;
import com.beginagain.hyclub00.adapter.SQLiteFunction;
import com.beginagain.hyclub00.data.ClubData;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment_MYCLUB extends Fragment implements OnItemClickListener, OnItemLongClickListener{
	private MySQLiteOpenHelper helper;
	private SQLiteDatabase db;
	private SQLiteFunction order;
	private ListView myList;
	private ArrayList<ClubData> allArray, selArray;
	private TextView intro;
	
	public Fragment_MYCLUB() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_myclub, container, false);
		
		helper = new MySQLiteOpenHelper(getActivity(), "club.db", null, 1);
		db = helper.getWritableDatabase();
		order = new SQLiteFunction(db, helper);
		
		myList = (ListView)view.findViewById(R.id.MyList);
		intro = (TextView)view.findViewById(R.id.Intro);
		
		allArray = new ArrayList<ClubData>();
		selArray = new ArrayList<ClubData>();
		
		allArray = order.selectAll();
		
		for(int i = 0; i < allArray.size(); i++){
			ClubData tmp = new ClubData();
			tmp = allArray.get(i);
			if(tmp.getCheck() == 1){
				selArray.add(tmp);
			}
		}
		
		
		DataAdapter adapter = new DataAdapter(getActivity(), getActivity().getBaseContext(), selArray);
		
		if(selArray.size() != 0)
			intro.setVisibility(View.GONE);
		else
			intro.setVisibility(View.VISIBLE);
		
		myList.setAdapter(adapter);
		
		myList.setOnItemClickListener(this);
		
		myList.setOnItemLongClickListener(this);
		
		return view;
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		
		ClubData longClub = (ClubData) myList.getItemAtPosition(position);
		final int selId = longClub.getId();
		final int selPos = position;
		
		AlertDialog.Builder alerDlg = new AlertDialog.Builder(getActivity());
		
		alerDlg.setTitle("MYCLUB").setCancelable(true);
		
		alerDlg.setMessage("해당 동아리를 'MYCLUB'에서 제외하시겠습니까?");
		
		alerDlg.setPositiveButton("확인", new OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				
				order.updateDB(selId, 0);
				
				selArray.remove(selPos);
				
				DataAdapter adapter = new DataAdapter(getActivity(), getActivity().getBaseContext(), selArray);
				
				if(selArray.size() != 0)
					intro.setVisibility(View.GONE);
				else
					intro.setVisibility(View.VISIBLE);
				
				myList.setAdapter(adapter);

				dialog.dismiss();
			}
		});
		alerDlg.setNegativeButton("취소", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
			
		});
		
		alerDlg.show();
		
		return false;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		Intent dataInfo = new Intent(getActivity(), InfoActivity.class);
		
		ClubData selData = (ClubData) myList.getItemAtPosition(position);
		
		dataInfo.putExtra("clubInfo", selData);
		
		startActivity(dataInfo);
	}
	
	

}
