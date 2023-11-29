package com.spinning_eggs.navigation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Route {
	public ArrayList<GeoPoint> route;
	public int num_of_node;
	public Route() {
		this.route = new ArrayList<GeoPoint>();
		
		SecureRandom  random = new SecureRandom ();
		this.num_of_node = random.nextInt(3, 8);		
		
		for (int i = 0; i < this.num_of_node; i++) {
			this.route.add(new GeoPoint());
		}
	}
	
	public Route(ArrayList<GeoPoint> r) {
		this.route = r;
	}
	
	public Route addPoint(GeoPoint g) {
		this.route.add(g);
		return this;
	}
	
	public Route addPoint(float lat, float lng) {
		GeoPoint g = new GeoPoint(lat, lng);
		this.route.add(g);
		return this;
	}
	
	public String toString() {
		return route.toString();
	}
	
	public String getJson() {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		Map<String, String> route = new LinkedHashMap<>();
		for(int i = 0; i < this.num_of_node; i++) {
			route.put(String.valueOf(i), this.route.get(i).getJson());
		}
		String json = gson.toJson(route);
		return json;
	}
}
