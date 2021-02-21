package com.example.action_bar;

import android.support.v4.app.Fragment;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.action_bar.JSONParser;

public class Page1 extends Fragment {
	ListView list;
	TextView sta;
	TextView cap;
	TextView lat;
	TextView lon;
	Button Btngetdata;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	// URL to get JSON Array
	private static String url = "http://www.json-generator.com/api/json/get/cuxNLbvUHS?indent=2";
	// JSON Node Names
	private static final String TAG_CIT = "citys";
	private static final String TAG_STA = "state";
	private static final String TAG_CAP = "capital";
	private static final String TAG_LAT = "latitude";
	private static final String TAG_LON = "longitude";
	JSONArray android = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page1, null);
		
		//tagofpage3 = ((MainActivity)getActivity()).getpage3tag();
				
		sta = (TextView) view.findViewById(R.id.sta);
		cap = (TextView) view.findViewById(R.id.cap);
		lat = (TextView) view.findViewById(R.id.lat);
		lon = (TextView) view.findViewById(R.id.lon);
		list = (ListView) view.findViewById(R.id.list);

		oslist = new ArrayList<HashMap<String, String>>();
		Btngetdata = (Button) view.findViewById(R.id.getdata);
		Btngetdata.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new JSONParse().execute();
			}
		});
		return view;
	}

	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			JSONParser jParser = new JSONParser();
			// Getting JSON from URL
			JSONObject json = jParser.getJSONFromUrl(url);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			try {
				// Getting JSON Array from URL
				android = json.getJSONArray(TAG_CIT);
				for (int i = 0; i < android.length(); i++) {
					JSONObject c = android.getJSONObject(i);
					// Storing JSON item in a Variable
					String sta = c.getString(TAG_STA);
					String cap = c.getString(TAG_CAP);
					String lat = c.getString(TAG_LAT);
					String lon = c.getString(TAG_LON);
					// Adding value HashMap key => value
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(TAG_STA, sta);
					map.put(TAG_CAP, cap);
					map.put(TAG_LAT, lat);
					map.put(TAG_LON, lon);

					oslist.add(map);

					ListAdapter adapter = new SimpleAdapter(getActivity(),
							oslist, R.layout.list_v, new String[] { TAG_STA,
									TAG_CAP, TAG_LAT, TAG_LON }, new int[] {
									R.id.sta, R.id.cap, R.id.lat, R.id.lon });
					list.setAdapter(adapter);
					list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							Toast.makeText(
									getActivity(),
									"You Clicked at "
											+ oslist.get(+position)
													.get("state"),
									Toast.LENGTH_SHORT).show();
							
							
							((MainActivity)getActivity()).setPageTag(1);
							
							
							}
					});
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
}