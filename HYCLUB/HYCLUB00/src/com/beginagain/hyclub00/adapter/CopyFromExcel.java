package com.beginagain.hyclub00.adapter;

import java.io.IOException;
import java.io.InputStream;

import com.beginagain.hyclub00.data.ClubData;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CopyFromExcel {
	private Context context;
	private SQLiteDatabase db;
	private MySQLiteOpenHelper helper;
	
	public CopyFromExcel(Context context, SQLiteDatabase db,
			MySQLiteOpenHelper helper) {
		super();
		this.context = context;
		this.db = db;
		this.helper = helper;
	}

	public void copyFromExcel(){
		SQLiteFunction order = new SQLiteFunction(db, helper);
		Workbook workbook = null;
		Sheet sheet = null;
		
		try {
			InputStream is = context.getResources().getAssets().open("ClubData06.xls");
			workbook = Workbook.getWorkbook(is);
			
			if(workbook != null){
				sheet = workbook.getSheet(0);
				
				if(sheet != null){
					int endRow = sheet.getColumn(0).length;
					
					for(int i = 0; i < endRow; i++){
						ClubData tmp = new ClubData();
						tmp.setId(order.selectId());
						tmp.setName(sheet.getCell(0, i).getContents());
						tmp.setCate(sheet.getCell(1, i).getContents());
						tmp.setRange(sheet.getCell(2, i).getContents());
						tmp.setP_name(sheet.getCell(3, i).getContents());
						tmp.setP_phone(sheet.getCell(4, i).getContents());
						tmp.setP_mail(sheet.getCell(5, i).getContents());
						tmp.setPeriod(sheet.getCell(6, i).getContents());
						tmp.setWay(sheet.getCell(7, i).getContents());
						tmp.setTarget(sheet.getCell(8, i).getContents());
						tmp.setMajor(sheet.getCell(9, i).getContents());
						tmp.setDay(sheet.getCell(10, i).getContents());
						tmp.setLoc(sheet.getCell(11, i).getContents());
						tmp.setSize(sheet.getCell(12, i).getContents());
						tmp.setPage(sheet.getCell(13, i).getContents());
						tmp.setDetail(sheet.getCell(14, i).getContents());
						tmp.setCareer(sheet.getCell(15, i).getContents());
						tmp.setPost(sheet.getCell(16, i).getContents());
						tmp.setPicture(sheet.getCell(17, i).getContents());
						tmp.setLogo(sheet.getCell(18, i).getContents());
						tmp.setPlus(sheet.getCell(19, i).getContents());
						tmp.setNum(sheet.getCell(20, i).getContents());
						tmp.setCheck(0);
						
						order.insertDB(tmp);
					}
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
