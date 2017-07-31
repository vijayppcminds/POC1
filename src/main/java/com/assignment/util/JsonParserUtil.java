package com.assignment.util;

import com.assignment.dto.RequestBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.SwingUtilities;

public class JsonParserUtil {

	public static Object makeCopy(Object[] data, int skipIndex) {
		Object[] newData = new Object[data.length - 1];
		int count = 0;
		for (int i = 0; i < data.length; i++) {
			if (i == skipIndex)
				continue;
			else {
				newData[count] = data[i];
				count++;
			}
		}
		return newData;
	}

	@SuppressWarnings("unchecked")
	public static String removeUnwantedMedia(String data, String filter, String removeMediaType) {
		Map<String, Object> retMap = new Gson().fromJson(data, new TypeToken<HashMap<String, Object>>() {
		}.getType());
		@SuppressWarnings({ "rawtypes" })
		List<Map> mediaList = (List<Map>) retMap.get(Utility.searchableItemsProp[1]);
		int size = mediaList.size();
		@SuppressWarnings("rawtypes")
		List newMediaList = new ArrayList();
		mediaList.forEach(item -> {
			@SuppressWarnings("rawtypes")
			Map tempMap = item;
			String guid = (String) (tempMap.containsKey(filter) ? tempMap.get(filter) : null);
			switch (removeMediaType) {
			case "censored":
				if (guid != null && guid.endsWith("C")) {
					newMediaList.add(item);
				}
				break;
			case "uncensored":
				if (guid != null && !guid.endsWith("C")) {
					newMediaList.add(item);
				}
				break;
			}
			/*if (guid != null && guid.endsWith("C")) {
				newMediaList.add(item);
			}*/
		});
		retMap.put(Utility.searchableItemsProp[1], newMediaList);
		return retMap.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String parseJSON(RequestBean bean, String filter) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			if (Utility.filteredProp == null) {
				Utility.loadProperties();
			}
			String file = ResourceUtils.getFile("classpath:contents.json").getPath();
			Object obj = parser.parse(new FileReader(file));

			jsonObject = (JSONObject) obj;
			String removeMediaType = "";
			
			switch(filter){
			case "-":
				return jsonObject.toJSONString();
			case "Censored":
				removeMediaType = "uncensored";				
				break;
			case "Uncensored":
				removeMediaType = "censored";
				break;
			}

			JSONArray msg = (JSONArray) jsonObject.get(Utility.searchableItemsProp[0]);
			/*com.assignment.dto.JSONBean staff = new Gson().fromJson(new FileReader(file),
					com.assignment.dto.JSONBean.class);*/
			Object[] data = msg.stream().toArray();

			int length = data.length;
			List finalList = new ArrayList();
			String temp = null;
			for (int i = 0; i < length; i++) {
				temp = data[i].toString();
				Map<String, Object> retMap = new HashMap<>();
				try{
				retMap = new Gson().fromJson(temp, new TypeToken<HashMap<String, Object>>() {
				}.getType());
				}catch(Exception e){
					
				}
				boolean isThere = retMap.containsKey(Utility.filteredProp[0]);
				if (isThere) {
					if (!retMap.get(Utility.filteredProp[0]).equals(bean.getLevel())) {
						// copy the data
						data = (Object[]) makeCopy(data, i);
						i = -1;
						length = data.length;

					} else {
						String temp1 = removeUnwantedMedia(temp, Utility.filteredProp[1], removeMediaType);
						data[i] = temp1;
						finalList.add(temp1);
					}
				}
			}
			Gson json = new Gson();
			String tempVal = json.toJson(finalList);
			jsonObject.put(Utility.searchableItemsProp[0], tempVal);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonObject.toJSONString();
	}
}
