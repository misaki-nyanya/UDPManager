package com.spinning_eggs.navigation;

import java.security.SecureRandom;

import com.google.gson.Gson;

public class Geo2DPoint{
	public Float x;
	public Float y;

	public Geo2DPoint() {
		SecureRandom  random = new SecureRandom ();
		this.x = random.nextFloat(121.490f, 121.492f);
		random.setSeed(System.currentTimeMillis());
		this.y = random.nextFloat(31.220f, 31.221f);
	}

	public Geo2DPoint(Float x, Float y) {
		this.x = x;
		this.y = y;
	}
		
	public String toString() {
		return "("+String.valueOf(x)+","+String.valueOf(this.y)+")";
	}
	
	public String getJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
