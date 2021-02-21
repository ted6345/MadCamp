package com.beginagain.hyclub00.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ClubData implements Parcelable {
	private String name, cate, range, p_name, p_phone, p_mail, period, way, target, major, day, loc, size, page, detail, career, post, picture, logo, plus, num;
	private int id, check;
	
	public ClubData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClubData(String name, String cate, String range, String p_name,
			String p_phone, String p_mail, String period, String way,
			String target, String major, String day, String loc, String size,
			String page, String detail, String career, String post,
			String picture, String logo, String plus, String num, int id, int check) {
		super();
		this.name = name;
		this.cate = cate;
		this.range = range;
		this.p_name = p_name;
		this.p_phone = p_phone;
		this.p_mail = p_mail;
		this.period = period;
		this.way = way;
		this.target = target;
		this.major = major;
		this.day = day;
		this.loc = loc;
		this.size = size;
		this.page = page;
		this.detail = detail;
		this.career = career;
		this.post = post;
		this.picture = picture;
		this.logo = logo;
		this.plus = plus;
		this.num = num;
		this.id = id;
		this.check = check;
	}
	
	public ClubData(Parcel in){
		String[] data = new String[23];
		
		in.readStringArray(data);
		this.name = data[0];
		this.cate = data[1];
		this.range = data[2];
		this.p_name = data[3];
		this.p_phone = data[4];
		this.p_mail = data[5];
		this.period = data[6];
		this.way = data[7];
		this.target = data[8];
		this.major = data[9];
		this.day = data[10];
		this.loc = data[11];
		this.size = data[12];
		this.page = data[13];
		this.detail = data[14];
		this.career = data[15];
		this.post = data[16];
		this.picture = data[17];
		this.logo = data[18];
		this.plus = data[19];
		this.num = data[20];
		this.id = Integer.parseInt(data[21]);
		this.check = Integer.parseInt(data[22]);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getP_phone() {
		return p_phone;
	}

	public void setP_phone(String p_phone) {
		this.p_phone = p_phone;
	}

	public String getP_mail() {
		return p_mail;
	}

	public void setP_mail(String p_mail) {
		this.p_mail = p_mail;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPlus() {
		return plus;
	}

	public void setPlus(String plus) {
		this.plus = plus;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeStringArray(new String[]{this.name, this.cate, this.range, this.p_name, this.p_phone, this.p_mail, this.period, this.way, this.target, this.major, this.day, this.loc, this.size, this.page, this.detail, this.career, this.post, this.picture, this.logo, this.plus, this.num, String.valueOf(this.id), String.valueOf(this.check)});
	}
	
	public static final Parcelable.Creator<ClubData> CREATOR = new Parcelable.Creator<ClubData>() {

		@Override
		public ClubData createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new ClubData(source);
		}

		@Override
		public ClubData[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ClubData[size];
		}
		
	};
}
