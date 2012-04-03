package com.roadierich;

import java.util.HashMap;


public class Scene {
	public HashMap<Integer, Integer> channels;
	public int time;
	public int id;
	static int lastId = 0;
	
	public Scene(HashMap<Integer, Integer> channels, int time)
	{
		this.channels = channels;
		this.time = time;
		this.id = lastId++;
	}
}
