/*
 * Copyright (C) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aceteam.game.battleship;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

/**
 * Basic turn data. It's just a blank data string and a turn number counter.
 * 
 * @author wolff
 * 
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class SkeletonTurn {

    public static final String TAG = "EBTurn";

    public String data = "";
    public int[][] boarddata = new int[10][10];
    public int[] x_coordinate = new int [10];
    public int[] y_coordinate = new int [10];
    public int count;


    
    public SkeletonTurn() {
    }

    // This is the byte array we will write out to the TBMP API.
    public byte[] persist() {
        JSONObject retVal = new JSONObject();

        JSONArray board = new JSONArray();
        for(int i = 0 ; i < 10; i ++){
    		JSONArray temp = new JSONArray();
        	for (int j = 0; j < 10; j ++){
        		temp.put(boarddata[i][j]);
        	}
        	board.put(temp);
        }
        
        JSONArray x_coor = new JSONArray();
        JSONArray y_coor = new JSONArray();
        for(int i = 0 ; i < 10; i ++){
        		x_coor.put(x_coordinate[i]);
        		y_coor.put(y_coordinate[i]);
        }
    
        try {
            retVal.put("data", data);
            retVal.put("boarddata", board);
            retVal.put("x_coordinate",x_coor);
            retVal.put("y_coordinate",y_coor);
            retVal.put("count", count);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String st = retVal.toString();

        Log.d(TAG, "==== PERSISTING\n" + st);

        return st.getBytes(Charset.forName("UTF-8"));
    }

    // Creates a new instance of SkeletonTurn.
    static public SkeletonTurn unpersist(byte[] byteArray) {

        if (byteArray == null) {
            Log.d(TAG, "Empty array---possible bug.");
            return new SkeletonTurn();
        }

        String st = null;
        try {
            st = new String(byteArray, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return null;
        }

        Log.d(TAG, "====UNPERSIST \n" + st);

        SkeletonTurn retVal = new SkeletonTurn();

        try {
            JSONObject obj = new JSONObject(st);

            if (obj.has("data")) {
                retVal.data = obj.getString("data");
            }
            if (obj.has("boarddata")) {
            	
            	 JSONArray board = obj.getJSONArray("boarddata");
                 for(int i = 0 ; i < 10; i ++){
             		JSONArray temp = board.getJSONArray(i);
                 	for (int j = 0; j < 10; j ++){
                 		retVal.boarddata[i][j] = temp.getInt(j); 
                 	}
                 }

            }
            if (obj.has("x_coordinate")) {
            	JSONArray x_coor = obj.getJSONArray("x_coordinate"); 
            	for(int i = 0 ; i < 10; i ++){
            		retVal.x_coordinate[i] = x_coor.getInt(i);
            	}
            }
            if (obj.has("y_coordinate")) {
            	JSONArray y_coor = obj.getJSONArray("y_coordinate"); 
            	for(int i = 0 ; i < 10; i ++){
            		retVal.y_coordinate[i] = y_coor.getInt(i);
            	}
            }
            if (obj.has("count")) {
                retVal.count = obj.getInt("count");
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retVal;
    }
}
