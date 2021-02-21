package com.example.tabmenu;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;


public class MainActivity extends Activity {
	private ViewPager mPager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // add three tabs to the action bar
        actionBar.addTab(actionBar.newTab().setText("A")
        		.setTabListener(new TabListener<FragmentTabA>(this, "tabA", FragmentTabA.class)));
        
        actionBar.addTab(actionBar.newTab().setText("B")
        		.setTabListener(new TabListener<FragmentTabB>(this, "tabB", FragmentTabB.class)));
        
        actionBar.addTab(actionBar.newTab().setText("C")
        		.setTabListener(new TabListener<FragmentTabC>(this, "tabC", FragmentTabC.class)));
       
        if(savedInstanceState != null)
        	actionBar.setSelectedNavigationItem(savedInstanceState.getInt("selectedTab", 0));
        
        setContentView(R.layout.activity_main);
        
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tabbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}
