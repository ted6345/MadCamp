package com.example.fragment.practice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Mainacivity extends Activity {

	private static String[] NUMBERS = new String[]{"1","2","3","4","5"};
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
    }
	public static class ArrayListFragment extends Fragment
	{
		public void onActivityCreated(Bundle savedInstanceState)
		{
			super.onActivityCreated(savedInstanceState);
			setAdapter(new ArrayAdapter<String>(getAcivity(),android.R.layout.simple_list_item_activated_1,NUMBERS));
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			
		}
	}
}
