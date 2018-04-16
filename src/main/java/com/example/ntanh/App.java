package com.example.ntanh;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.ntanh.dao.ExelRecommendDao;
import com.example.ntanh.dao.RecommendDao;
import com.example.ntanh.entity.Recommend;

/**
 * Hello world!
 *
 */
public class App 
{
	final static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
    	RecommendDao dao = new ExelRecommendDao();
    	Map<String, Recommend> mapData = dao.read();
    }
}
