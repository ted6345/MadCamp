package com.beginagain.hyclub00.adapter;

import java.util.ArrayList;

import com.beginagain.hyclub00.data.ClubData;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteFunction {
	private SQLiteDatabase db;
	private MySQLiteOpenHelper helper;
	
	public SQLiteFunction(SQLiteDatabase db, MySQLiteOpenHelper helper) { // 생성자
		super();
		this.db = db; // db 세팅
		this.helper = helper; // helper 세팅
	}
	
	public void insertDB(ClubData data){
		ContentValues values = new ContentValues();
		
		values.put("id", data.getId());
		values.put("name",	data.getName());
		values.put("cate", data.getCate());
		values.put("range", data.getRange());
		values.put("pname", data.getP_name());
		values.put("pphone", data.getP_phone());
		values.put("pmail", data.getP_mail());
		values.put("period", data.getPeriod());
		values.put("way", data.getWay());
		values.put("target", data.getTarget());
		values.put("major", data.getMajor());
		values.put("day", data.getDay());
		values.put("loc", data.getLoc());
		values.put("size", data.getSize());
		values.put("page", data.getPage());
		values.put("detail", data.getDetail());
		values.put("career", data.getCareer());
		values.put("post", data.getPost());
		values.put("picture", data.getPicture());
		values.put("logo", data.getLogo());
		values.put("plus", data.getPlus());
		values.put("num", data.getNum());
		values.put("checked", data.getCheck());
		
		db.insert("club_data", null, values);
	}
	
	public void updateDB(int id, int check){
		ContentValues values = new ContentValues();
		
		values.put("checked", check);
		
		db.update("club_data", values, "id=?", new String[]{id + ""});
	}
	
	public void deleteDB(int id){
		
		db.delete("club_data", "id=?", new String[]{id + ""}); // id값으로 해당 DB찾아서 delete!
		
	}
	
	public ClubData selectDB(int id){
		ClubData data = new ClubData();
		
		Cursor c = db.query("club_data", null, "id=?", new String[]{id + ""}, null, null, null);
		
		while(c.moveToNext()){
			data.setId(id);
			data.setName(c.getString(c.getColumnIndex("name")));
			data.setCate(c.getString(c.getColumnIndex("cate")));
			data.setRange(c.getString(c.getColumnIndex("range")));
			data.setP_name(c.getString(c.getColumnIndex("pname")));
			data.setP_phone(c.getString(c.getColumnIndex("pphone")));
			data.setP_mail(c.getString(c.getColumnIndex("pmail")));
			data.setPeriod(c.getString(c.getColumnIndex("period")));
			data.setWay(c.getString(c.getColumnIndex("way")));
			data.setTarget(c.getString(c.getColumnIndex("target")));
			data.setMajor(c.getString(c.getColumnIndex("major")));
			data.setDay(c.getString(c.getColumnIndex("day")));
			data.setLoc(c.getString(c.getColumnIndex("loc")));
			data.setSize(c.getString(c.getColumnIndex("size")));
			data.setPage(c.getString(c.getColumnIndex("page")));
			data.setDetail(c.getString(c.getColumnIndex("detail")));
			data.setCareer(c.getString(c.getColumnIndex("career")));
			data.setPost(c.getString(c.getColumnIndex("post")));
			data.setPicture(c.getString(c.getColumnIndex("picture")));
			data.setLogo(c.getString(c.getColumnIndex("logo")));
			data.setPlus(c.getString(c.getColumnIndex("plus")));
			data.setNum(c.getString(c.getColumnIndex("num")));
			data.setCheck(c.getInt(c.getColumnIndex("checked")));
		}
		
		return data;
	}
	
	public ArrayList<ClubData> selectAll(){
		ArrayList<ClubData> db_list = new ArrayList<ClubData>();
		
		Cursor c = db.query("club_data", null, null, null, null, null, null);
		
		while(c.moveToNext()){
			ClubData data = new ClubData();
			
			data.setId(c.getInt(c.getColumnIndex("id")));
			data.setName(c.getString(c.getColumnIndex("name")));
			data.setCate(c.getString(c.getColumnIndex("cate")));
			data.setRange(c.getString(c.getColumnIndex("range")));
			data.setP_name(c.getString(c.getColumnIndex("pname")));
			data.setP_phone(c.getString(c.getColumnIndex("pphone")));
			data.setP_mail(c.getString(c.getColumnIndex("pmail")));
			data.setPeriod(c.getString(c.getColumnIndex("period")));
			data.setWay(c.getString(c.getColumnIndex("way")));
			data.setTarget(c.getString(c.getColumnIndex("target")));
			data.setMajor(c.getString(c.getColumnIndex("major")));
			data.setDay(c.getString(c.getColumnIndex("day")));
			data.setLoc(c.getString(c.getColumnIndex("loc")));
			data.setSize(c.getString(c.getColumnIndex("size")));
			data.setPage(c.getString(c.getColumnIndex("page")));
			data.setDetail(c.getString(c.getColumnIndex("detail")));
			data.setCareer(c.getString(c.getColumnIndex("career")));
			data.setPost(c.getString(c.getColumnIndex("post")));
			data.setPicture(c.getString(c.getColumnIndex("picture")));
			data.setLogo(c.getString(c.getColumnIndex("logo")));
			data.setPlus(c.getString(c.getColumnIndex("plus")));
			data.setNum(c.getString(c.getColumnIndex("num")));
			data.setCheck(c.getInt(c.getColumnIndex("checked")));
			
			db_list.add(data);
		}
		
		return db_list;
	}
	
	public int selectId(){
		int id = -1;
		Cursor c = db.query("club_data", null, null, null, null, null, null);
		
		while(c.moveToNext())
			id = c.getInt(c.getColumnIndex("id"));
		
		return (id+1);
	}
}
