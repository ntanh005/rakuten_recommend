package com.example.ntanh.dao;

import java.util.Map;

import com.example.ntanh.entity.Recommend;

public interface RecommendDao {
	Map<String, Recommend> read();
}
