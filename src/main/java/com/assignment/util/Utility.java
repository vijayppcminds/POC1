package com.assignment.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.ResourceUtils;

import com.assignment.dto.RequestBean;

public class Utility {

	private static Properties prop = null;
	public static String[] filteredProp = null;
	public static String[] searchableItemsProp = null;
	
	public static void loadProperties(){
		//ResourceUtils.getFile("classpath:mediaProperties.properties").getPath()
		if(prop == null){
			InputStream input = null;
			try {
				prop = new Properties();
				//input = new FileInputStream("C:/Users/A C/Downloads/assignment1/assignment1/src/main/resources/mediaProperties.properties");
				input = new FileInputStream(ResourceUtils.getFile("classpath:mediaProperties.properties").getPath());
				try {
					prop.load(input);
					String temp = prop.getProperty(MediaEnum.FILTERED_VALUES.getParam());
					filteredProp=temp.split(MediaEnum.SEMI_COLON.getParam());
					temp = null;
					temp = prop.getProperty(MediaEnum.SEARCHABLE_ITEMS.getParam());
					searchableItemsProp=temp.split(MediaEnum.SEMI_COLON.getParam());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}finally {
				if(input != null){
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static boolean validateEmptyCheck(String data){
		if(data.equals(""))
		return false;
		else
			return true;
	}
	
	public static boolean validatePatternCheck(String data, MediaEnum regex){
		Pattern pattern = Pattern.compile(regex.getParam());
        Matcher	 matcher = pattern.matcher(data);
        if(matcher.matches())return true;
		return false;
	}
	
	public static boolean validateInputParameters(Object obj, MediaEnum objType){
		switch (objType) {
		case REQUESTBEAN:
			RequestBean bean = (RequestBean)obj;
			if(bean.getFilter() != null){
				if(!validateEmptyCheck(bean.getFilter())) return false;
				if(!validatePatternCheck(bean.getFilter(), MediaEnum.FILTER_REGEX)) return false;;
			}if(bean.getLevel() != null){
				if(!validateEmptyCheck(bean.getLevel()))return false;;
				if(!validatePatternCheck(bean.getLevel(), MediaEnum.LEVEL_REGEX))return false;;
			}
			break;

		default:
			break;
		}
		return true;
	}
	
	public static Properties getPropertiesObj(){
		if(prop == null)loadProperties();
		return prop;
	}
	
}
