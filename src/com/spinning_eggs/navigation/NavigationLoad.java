package com.spinning_eggs.navigation;

import java.security.SecureRandom;
import java.util.ArrayList;
import com.google.gson.Gson;

public class NavigationLoad {
	public Geo2DPoint current;
	public Route naviRoute;
	public float usrDirection;
	public ArrayList<Route> envRoute;
	public int num_of_envRoute;
	
	public NavigationLoad() {
		this.current = new Geo2DPoint();
		this.naviRoute = new Route();
		this.envRoute = new ArrayList<Route>();
		
		SecureRandom  random = new SecureRandom();		
		this.num_of_envRoute = random.nextInt(3, 8);
		for (int i = 0; i < this.num_of_envRoute; i++) {
			this.envRoute.add(new Route());
		}
		this.usrDirection = random.nextFloat(-10, 10);
	}
	
	public NavigationLoad(Geo2DPoint cur, Route navi, ArrayList<Route> env){
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
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public void step() {
		SecureRandom  random = new SecureRandom();
		this.usrDirection = random.nextFloat(-90, 90);
		this.current.x = random.nextFloat(this.current.x-0.5f,
													this.current.x+0.5f);
		this.current.y = random.nextFloat(this.current.y-0.5f,
													this.current.y+0.5f);
	}
	
	public static void main(String[] args) {
		NavigationLoad test = new NavigationLoad();
		System.out.println(test.getJson());		
	}
}
