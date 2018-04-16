package com.example.ntanh.entity;

import java.util.ArrayList;
import java.util.List;

public class Recommend {
	String name;
	String parent;
	List<Recommend_Child> childs;

	public Recommend(String name, String parent) {
		this.name = name;
		this.parent = parent;
		childs = new ArrayList<Recommend_Child>();
	}

	public void add(Recommend_Child child) {
		childs.add(child);
	}
}
