package com.beginagain.hyclub00.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	public MySQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table club_data(" +
					"id integer primary key autoincrement," +
					"name text," +
					"cate text," +
					"range text," +
					"pname text," +
					"pphone text," +
					"pmail text," +
					"period text," +
					"way text," +
					"target text," +
					"major text," +
					"day text," +
					"loc text," +
					"size text," +
					"page text," +
					"detail text," +
					"career text," +
					"post text," +
					"picture text," +
					"logo text," +
					"plus text," + 
					"num text," +
					"checked integer);";
		
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //이거 쓰임은 잘 모르겟네요.. 쏴리 여러분 ㅠㅠ
		String sql = "drop table if exists club";
		db.execSQL(sql);
		
		onCreate(db);
	}

}
