package com.spinning_eggs.navigation;

import java.security.SecureRandom;
import java.util.ArrayList;
import com.google.gson.Gson;

public class Route {
	public ArrayList<Geo2DPoint> route;
	public int num_of_node;
	public Route() {
		this.route = new ArrayList<Geo2DPoint>();
		
		SecureRandom  random = new SecureRandom ();
		this.num_of_node = random.nextInt(3, 8);		
		
		for (int i = 0; i < this.num_of_node; i++) {
			this.route.add(new Geo2DPoint());
		}
	}
	
	public Route(ArrayList<Geo2DPoint> r) {
		this.route = r;
	}
	
	public Route addGeo2DPoint(Geo2DPoint g) {
		this.route.add(g);
		return this;
	}
	
	public Route addGeo2DPoint(Float data1, Float data2) {
		Geo2DPoint g = new Geo2DPoint(data1, data2);
		this.route.add(g);
		return this;
	}
	
	public String toString() {
		return route.toString();
	}
	
	public String getJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	public static void main(String[] args) {
		Route test = new Route();
		System.out.println(test.getJson());		
	}
}
