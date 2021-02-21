package com.example.action_bar_1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class newArrayAdapter extends ArrayAdapter<String>{

    Context context; 
    int layoutResourceId;    
    ArrayList<String> data = new ArrayList<String>();
    
    public newArrayAdapter(Context context, int layoutResourceId, ArrayList<String> data) {
        super(context, layoutResourceId, data);
        
        this.layoutResourceId = layoutResourceId;
        
        this.context = context;
        
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TextView text = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            
            
            text = (TextView)row.findViewById(R.id.text1);
            
            row.setTag(text);
            
        }
        else
        {
            text = (TextView)row.getTag();
        }
        
        String text_to_write = data.get(position);
        text.setText(text_to_write);
        
        return row;
    }
}