package com.spinning_eggs.navigation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NavigationLoad {
	public GeoPoint current;
	public Route naviRoute;
	public ArrayList<Route> envRoute;
	public int num_of_envRoute;
	
	public NavigationLoad() {
		this.current = new GeoPoint();
		this.naviRoute = new Route();
		this.envRoute = new ArrayList<Route>();
		
		SecureRandom  random = new SecureRandom();		
		this.num_of_envRoute = random.nextInt(3, 8);
		for (int i = 0; i < this.num_of_envRoute; i++) {
			this.envRoute.add(new Route());
		}
		
	}
	
	public NavigationLoad(GeoPoint cur, Route navi, ArrayList<Route> env){
		this.current = cur;
		this.naviRoute = navi;
		this.envRoute = env;
		this.num_of_envRoute = env.size();
	}
	
	public String toString() {
		String val = "current:" + current.toString() + ",\n";
		val += "naviRoute:" + naviRoute.toString() + ",\n";
		val += "envRoute:" + envRoute.toString() + ",\n";
		return val;
	}
	
	public String getJson() {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		Map<String, String> navi = new LinkedHashMap<>();
		navi.put("pos", this.current.getJson());
		navi.put("nav", this.naviRoute.getJson());
		
		Map<String, String> envr = new LinkedHashMap<>();
		for(int i = 0; i < this.num_of_envRoute; i++) {
			envr.put(String.valueOf(i), this.envRoute.get(i).getJson());
		}
		String json = gson.toJson(envr);
		navi.put("env", json);
		
		json = gson.toJson(navi);
		return json;
	}
}
