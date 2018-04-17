package com.example.ntanh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

/**
 * Hello world!
 *
 */
public class App 
{
	final static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
    	TestListenerAdapter tla = new TestListenerAdapter();
    	TestNG testng = new TestNG();
    	testng.setTestClasses(new Class[] { AppTest.class });
    	testng.addListener(tla);
    	testng.run();
    }
}
