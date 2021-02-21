package com.beginagain.hyclub00;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.beginagain.hyclub00.adapter.DataAdapter;
import com.beginagain.hyclub00.adapter.MySQLiteOpenHelper;
import com.beginagain.hyclub00.adapter.SQLiteFunction;
import com.beginagain.hyclub00.data.ClubData;

public class Fragment_HYCLUB extends Fragment implements OnItemSelectedListener, OnItemLongClickListener, OnItemClickListener{
	private MySQLiteOpenHelper helper;
	private SQLiteDatabase db;
	private SQLiteFunction order;
	private Spinner cateSpin, rangeSpin, majorSpin;
	private ListView clubList;
	private ArrayList<ClubData> allArray;
	private String selCate, selRange, selMajor;
	private int isMajor = 0;
	
	public Fragment_HYCLUB() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_hyclub, container, false);
		
		helper = new MySQLiteOpenHelper(getActivity(), "club.db", null, 1);
		db = helper.getWritableDatabase();
		order = new SQLiteFunction(db, helper);
		
		cateSpin = (Spinner)view.findViewById(R.id.CateSpin);
		rangeSpin = (Spinner)view.findViewById(R.id.RangeSpin);
		majorSpin = (Spinner)view.findViewById(R.id.MajorSpin);
		clubList = (ListView)view.findViewById(R.id.ClubList);
		
		allArray = new ArrayList<ClubData>();
		
		allArray = order.selectAll();
		
		DataAdapter adapter = new DataAdapter(getActivity(), getActivity().getBaseContext(), allArray);
		
		clubList.setAdapter(adapter);
		
		ArrayAdapter<CharSequence> cateAdapt = ArrayAdapter.createFromResource(getActivity(), R.array.selCate, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> rangeAdapt = ArrayAdapter.createFromResource(getActivity(), R.array.selRange, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> majorAdapt = ArrayAdapter.createFromResource(getActivity(), R.array.selMajor, android.R.layout.simple_spinner_item);
		
		cateAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		rangeAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		majorAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		cateSpin.setAdapter(cateAdapt);
		rangeSpin.setAdapter(rangeAdapt);
		majorSpin.setAdapter(majorAdapt);
		
		cateSpin.setOnItemSelectedListener(this);
		rangeSpin.setOnItemSelectedListener(this);
		majorSpin.setOnItemSelectedListener(this);
		
		clubList.setOnItemClickListener(this);
		
		clubList.setOnItemLongClickListener(this);
		
		return view;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		
		ArrayList<ClubData> selArray = new ArrayList<ClubData>();
		selCate = cateSpin.getSelectedItem().toString();
		selRange = rangeSpin.getSelectedItem().toString();
		
		if(selRange.equals("과")){
			isMajor = 1;
			majorSpin.setVisibility(View.VISIBLE);
		} else {
			isMajor = 0;
			majorSpin.setVisibility(View.INVISIBLE);
		}

		selMajor = majorSpin.getSelectedItem().toString();
		
		for(int i = 0; i < allArray.size(); i++){
			
			ClubData tmp = new ClubData();
			tmp = allArray.get(i);
			
			if(tmp.getCate().contains(selCate) && tmp.getRange().contains(selRange)){
				if(isMajor == 1){
					if(tmp.getMajor().contains(selMajor)){
						selArray.add(tmp);
					}
				} else {
					selArray.add(tmp);
				}
			}
		}
		
		DataAdapter selAdapt = new DataAdapter(getActivity(), getActivity().getBaseContext(), selArray);
		
		clubList.setAdapter(selAdapt);
		
		
//		switch(view.getId()){
//		case R.id.CateSpin:
//			
//			break;
//		case R.id.RangeSpin:
//			
//			break;
//		case R.id.MajorSpin:
//			
//			break;
//		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		
		ClubData longClub = (ClubData) clubList.getItemAtPosition(position);
		final int selId = longClub.getId();
		final int selCheck = longClub.getCheck();
		
		AlertDialog.Builder alerDlg = new AlertDialog.Builder(getActivity());
		
		alerDlg.setTitle("MYCLUB").setCancelable(true);
		
		if(selCheck == 0)
			alerDlg.setMessage("해당 동아리를 'MYCLUB'에 추가하시겠습니까?");
		else if(selCheck == 1)
			alerDlg.setMessage("해당 동아리를 'MYCLUB'에서 제외하시겠습니까?");
		
		alerDlg.setPositiveButton("확인", new OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				
				if(selCheck == 0)
					order.updateDB(selId, 1);
				else if(selCheck == 1)
					order.updateDB(selId, 0);

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
		
		ClubData selData = (ClubData) clubList.getItemAtPosition(position);
		
		dataInfo.putExtra("clubInfo", selData);
		
		startActivity(dataInfo);
	}
}
