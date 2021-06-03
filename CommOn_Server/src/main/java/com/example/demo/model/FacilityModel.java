package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class FacilityModel {
	private int num;
	private String facility_name;
	private String name;
	private String location;		// 대략적 위치 (수도권 등)
	private double longitude;
	private double latitude;
	private String line;
	private double distance;
	private int time;
	private double rating;
	private List<Integer> eachTime = new ArrayList<>();
	private String timeList;
	
	public String getFacility_name() {
		return facility_name;
	}
	public void setFacility_name(String facility_name) {
		this.facility_name = facility_name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getLine() {
		return line;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public List<Integer> getEachTime() {
		return this.eachTime;
	}
	public void setEachTime(int time) {
		this.eachTime.add(time);
	}
	public String getTimeList() {
		return timeList;
	}
	public void setTimeList(String timeList) {
		this.timeList = timeList;
	}
}
