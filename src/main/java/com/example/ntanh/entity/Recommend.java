package com.example.ntanh.entity;

import java.util.ArrayList;
import java.util.List;

public class Recommend {
	public String name;
	public String parent;
	public List<Recommend_Child> childs;

	public Recommend(String name, String parent) {
		this.name = name;
		this.parent = parent;
		childs = new ArrayList<Recommend_Child>();
	}

	public void add(Recommend_Child child) {
		childs.add(child);
	}

	@Override
	public String toString() {
		return "Recommend [code=" + parent +  "]";
	}
	
	
}
