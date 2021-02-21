package com.example.finalbrowser;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class BookMarkDialog extends DialogFragment{
	List <String> bookmark = new ArrayList<String>();
	List <String> bookmark_name = new ArrayList<String>();
	String[] listbookmark;
	ListView listview;
	Button Addbtn;
	View view ;
	Context mContext;
	
	public BookMarkDialog()
	{
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.dialog, container);
		getDialog().setTitle("BOOKMARK");
		listview = (ListView) view.findViewById(R.id.listView1);		
		return view;
	}
	
	@Override
	public void onActivityCreated(final Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		
	}
	@Override
	public void onResume()
	{
		super.onResume();
		loadArray();
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview_,listbookmark);
//		final CustomAdapter adapter = new CustomAdapter(mContext,R.layout.listview_,(ArrayList<String>) bookmark_name);
		adapter.setNotifyOnChange(true);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener(){
	
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				((MainActivity)getActivity()).getWeb().loadUrl(bookmark.get(position));
				dismiss();
			}	
		});
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				bookmark.remove(arg2);
				bookmark_name.remove(arg2);
				saveArray();
				loadArray();
				adapter.notifyDataSetChanged();
				listview.invalidate();
				onResume();
				return false;
			}
		
		});
		
	
	    

//		Addbtn.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				boolean overwrap=true;
//				for(int i = 0 ; i < bookmark.size();i++)
//				{
//					if(((MainActivity)getActivity()).getWeb().getUrl().equals(bookmark.get(i)))
//					overwrap=false ;	
//				}
//				if(overwrap&&((MainActivity)getActivity()).getWeb().getUrl().length()>3){
//				bookmark.add(((MainActivity)getActivity()).getWeb().getUrl());
//				saveArray();
//				loadArray();
//				adapter.notifyDataSetChanged();
//				listview.invalidateViews();
//				onResume();
//				}
//				else {
//					//toast ¶ç¿ì±â
//					Toast.makeText(getActivity(), "Invalid Add", Toast.LENGTH_SHORT).show();
////					getActivity().getFragmentManager().popBackStack();
//				}
//				dismiss();
//			}
//		});
//		
	}
	
	public void saveArray()
	{
		SharedPreferences pref = getActivity().getPreferences(0);
	    SharedPreferences.Editor mEdit1 = pref.edit();
	    mEdit1.putInt("Bookmark_size", bookmark.size()); 
	    for(int i=0;i<bookmark.size();i++)  
	    {
	        mEdit1.remove("Bookmark_" + i);
	        mEdit1.putString("Bookmark_" + i, bookmark.get(i));
	    }
	    for(int i=0;i<bookmark.size();i++)  
	    {
	        mEdit1.remove("Bookmark_name" + i);
	        mEdit1.putString("Bookmark_name" + i, bookmark_name.get(i));
	    }
	    mEdit1.commit();
	}	
	public void loadArray()
	{  
		SharedPreferences pref = getActivity().getPreferences(0);
		bookmark.clear();
	    int size = pref.getInt("Bookmark_size", 0);  
	    Log.v("Debugging","bookmarksize"+size);
	    for(int i=0;i<size;i++) 
	    {
	        bookmark.add(pref.getString("Bookmark_" + i, null));
	    }
	    for(int i=0;i<size;i++) 
	    {
	        bookmark_name.add(pref.getString("Bookmark_name" + i, null));
	    }
	    listbookmark = new String[size];
	    for(int i=0;i<size;i++) 
	    {
	    	listbookmark[i] = bookmark_name.get(i);
	    }
	}
	
}
	
	
