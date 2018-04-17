package com.example.ntanh.dao;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.example.ntanh.entity.Recommend;
import com.example.ntanh.entity.Recommend_Child;

public class ExelRecommendDao implements RecommendDao {
	private static final int NAME_COL = 0;
	private static final int PARENT_CODE_COL = 1;
	private static final int CHILD_CODE_COL = 2;
	private static final int CHILD_PRI_COL = 3;
	private static final String FILENAME = "data.xls";
	String fileName;	 

	public ExelRecommendDao() {
		this.fileName = FILENAME;
	}

	public Map<String, Recommend> read() {
		Map<String, Recommend> map = new HashMap<String, Recommend>();
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			fs = new POIFSFileSystem(new FileInputStream(fileName));
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			String parentCode ;
			String name;
			String childCode ;
			int priority;
			for(int irow = 0; irow < rows; irow++){
				row = sheet.getRow(irow);
				parentCode = row.getCell(PARENT_CODE_COL).getStringCellValue();
				name = row.getCell(NAME_COL).getStringCellValue();
				if(map.get(parentCode) == null){
					map.put(parentCode, new Recommend(name, parentCode));
				}				
				childCode = row.getCell(CHILD_CODE_COL).getStringCellValue(); 
				priority = (int)row.getCell(CHILD_PRI_COL).getNumericCellValue();
				map.get(parentCode).add(new Recommend_Child(childCode, priority));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return map;
	}
	
	public Object[][] getData(){
		Map<String, Recommend> map = read();
		Object[][] res = new Object[map.size()][1];
		int idx = 0;
		for(String key : map.keySet()){
			res[idx++][0] = map.get(key);			
		}
		return res;
	}

}
