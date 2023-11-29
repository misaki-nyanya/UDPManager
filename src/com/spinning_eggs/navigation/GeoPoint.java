package com.spinning_eggs.navigation;

import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GeoPoint{
	public float latitude;
	public float longtitude;
	
	public GeoPoint() {
		SecureRandom  random = new SecureRandom ();
		this.latitude = random.nextFloat(-90, 90);
		random.setSeed(System.currentTimeMillis());
		this.longtitude = random.nextFloat(-180, 180);
	}
	public GeoPoint(float d, float e) {
		this.latitude = d;
		this.longtitude = e;
	}
		
	public String toString() {
		return "("+String.valueOf(latitude)+","+String.valueOf(this.longtitude)+")";
	}
	
	public String getJson() {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		Map<String, Float> posi = new LinkedHashMap<>();
		posi.put("lat", this.latitude);
		posi.put("lng", this.longtitude);
		String json = gson.toJson(posi);
		return json;
	}
}
