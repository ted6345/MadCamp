package com.example.action_bar_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.action_bar_1.JSONParser;
import com.example.action_bar_1.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	ViewPager Tab;
	TabPagerAdapter TabAdapter;
	ActionBar actionBar;
	int pos;
	String messageToPass;
	boolean viewpager;

	//method to set and get data from Activity to Fragment
	public void setMessage(String t) { messageToPass = t; }
	public String getMessage() { return messageToPass; }
	public void setPos(int i) { pos = i; }
	public int getPos() { return pos; }
	public ArrayList<ArrayList<HashMap<String, String>>> getlist_of_oslist() {
		return list_of_oslist;
	}
	public int getPageTag(){
		int tag = Integer.parseInt(Tab.getTag() + "");
		return tag;
	}
	
	public void setPageTag(int tag){
		Tab.setTag(tag);
	}
	 
	public HashMap<String, HashMap<String, String>> getcate_and_menu() {
		return cate_and_menu;
	}
	
	public ArrayList<String> getCategories() {
		Set<String> key_cate = cate_and_menu.keySet();
		ArrayList<String> categories = new ArrayList<String>();

		for (String str : key_cate) {
			categories.add(str);
		}
		return categories;
	}
	
	public void setPagerCurrentPage(int pos){
		pos_frag = pos;
		Tab.setCurrentItem(pos);
	}
	
	
	String selectedCategory = null;
	public void setSelectedCategory(String t) {
		selectedCategory = t;
	}
	public String getSelectedCategory() {
		return selectedCategory;
	}
	
	String selectedFood = "선택되지 않음";
	public void setSelectedFood(String t) {
		selectedFood = t;
	}
	public String getSelectedFood() {
		return selectedFood;
	}
	
	int tmpposition;
	
	public void settmpposition(int i) { tmpposition = i; }
	public int gettmpposition() { return tmpposition; }
	
	
	int pos_frag = 0;
	
	//variable for JSONParsing
	ListView list;
	TextView sta;
	TextView cap;
	TextView lat;
	TextView lon;
	Button Btngetdata;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	ArrayList<ArrayList<HashMap<String, String>>> list_of_oslist = new ArrayList<ArrayList<HashMap<String, String>>>(); 
	// URL to get JSON Array
	private static String url = "http://www.json-generator.com/api/json/get/ccFnOIGvOq?indent=2";
	// JSON Node Names
	JSONObject categories_j;
	HashMap<String, HashMap<String, String>> cate_and_menu = new HashMap<String, HashMap<String, String>>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("맛있겠다아");
		new JSONParse().execute();
		
		viewpager = false;

	}
	
	
		
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	
	    	switch(pos_frag) {
	    	case 0:
	    		System.exit(1);
	    		break;
	    	case 1:
	    		pos_frag = 0;
	    		Tab.setCurrentItem(0);
	    		break;
	    	case 2:
	    		pos_frag = 1;
	    		Tab.setCurrentItem(1);
	    		break;
	    	case 3:
	    		pos_frag = 2;
	    		Tab.setCurrentItem(2);
	    		break;
	    	}
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(MainActivity.this);

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
				categories_j = json.getJSONObject("head");

				

				Iterator<String> cate_names = categories_j.keys();
				while (cate_names.hasNext()) {
					try {
						String cate_name = cate_names.next();
						JSONObject menu_j = categories_j
								.getJSONObject(cate_name);
						Iterator<String> menu_names = menu_j.keys();
						HashMap<String, String> menuname_and_dir = new HashMap<String, String>();
						while (menu_names.hasNext()) {
							try {
								String menu_name = menu_names.next();
								menuname_and_dir.put(menu_name,
										menu_j.getString(menu_name));
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						cate_and_menu.put(cate_name, menuname_and_dir);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}	
			
			ArrayList<String> categories = getCategories();
			Collections.sort(categories.subList(1, categories.size()));
			
			setSelectedCategory(categories.get(0));
			

			myHandler.sendEmptyMessage(0);
		}
	}		
	
	Handler myHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
				Tab = (ViewPager) findViewById(R.id.pager);
				Tab.setPageTransformer(true, new ZoomOutPageTransformer());

				Tab.setOffscreenPageLimit(1);
				Tab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar = getActionBar();
						switch(position) {
						case 0:
							pos_frag = 0;
							actionBar.setSelectedNavigationItem(0);
							viewpager = false;
							break;
						case 1:
							pos_frag = 1;
							actionBar.setSelectedNavigationItem(0);
							viewpager = false;
							break;
						case 2:
							pos_frag = 2;
							actionBar.setSelectedNavigationItem(1);
							viewpager = true;
							break;
						case 3:
							pos_frag = 3;
							actionBar.setSelectedNavigationItem(2);
							viewpager = false;
							break;
						}
						TabAdapter.notifyDataSetChanged();

					}
					
					
				});
				Tab.setAdapter(TabAdapter);

				actionBar = getActionBar();
				actionBar.setBackgroundDrawable(new ColorDrawable(0xffffffff));
				actionBar.setStackedBackgroundDrawable(new ColorDrawable(0xffff6b6b));
				int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
				TextView abTitle = (TextView) findViewById(titleId);
				abTitle.setTextColor(0xff1c140d);
				
				// Enable Tabs on Action Bar
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				ActionBar.TabListener tabListener = new ActionBar.TabListener() {
					@Override
					public void onTabReselected(android.app.ActionBar.Tab tab,
							FragmentTransaction ft) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onTabSelected(ActionBar.Tab tab,
							FragmentTransaction ft) {
						switch (tab.getPosition()) {
						case 0:
							if(viewpager) {
								pos_frag = 1;
								setPagerCurrentPage(1);
							}
							else {
								pos_frag = 0;
								setPagerCurrentPage(0);
							}
							viewpager = false;
							break;
						case 1:
							pos_frag = 2;
							setPagerCurrentPage(2);
							break;
						case 2:
							pos_frag = 3;
							setPagerCurrentPage(3);
							break;
						}
						
						
					}

					@Override
					public void onTabUnselected(android.app.ActionBar.Tab tab,
							FragmentTransaction ft) {
						// TODO Auto-generated method stub
					}
				};
				// Add New Tab
				actionBar.addTab(actionBar.newTab().setText("종류")
						.setTabListener(tabListener));
				actionBar.addTab(actionBar.newTab().setText("메뉴")
						.setTabListener(tabListener));
				actionBar.addTab(actionBar.newTab().setText("평가")
						.setTabListener(tabListener));

				Tab.setCurrentItem(0);
				break;
			default:
				break;
			}

		}

	};
	
}