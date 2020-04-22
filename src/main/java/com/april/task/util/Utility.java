package com.april.task.util;

import java.util.List;

import com.april.task.model.BitPayModel;
import com.april.task.model.ResponseResult;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;


public class Utility {

	public static boolean isNumericValue(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isEmptyString(String string) {
	    return string == null || string.isEmpty();
	}
	
	public static boolean isModelValidated(BitPayModel bitPayModel) {
		return (bitPayModel != null && !isEmptyString(bitPayModel.getCode()) && !isEmptyString(bitPayModel.getName()));
	}
	
	public static String convertToJson(BitPayModel bitPayModel) throws JsonProcessingException
	{
		Object object = bitPayModel;
		return serializeIt(object);
	}
	
	public static String convertToJson(List<BitPayModel> list) throws JsonProcessingException
	{
		Object object = list;
		return serializeIt(object);
	}
	
	public static String convertToJson(ResponseResult responseResult) throws JsonProcessingException
	{
		Object object = responseResult;
		return serializeIt(object);
	}

	public static String convertToJson(String input) throws JsonProcessingException
	{
		Object object = input;
		return serializeIt(object);
	}
	
	
	public static String serializeIt(Object object) throws JsonProcessingException {
		String jsonResult = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			jsonResult = mapper.writeValueAsString(object);
			System.out.println(jsonResult);
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		}
		
    	return jsonResult;
	}
	
	public static BitPayModel deserializeBitPay(String json) throws JsonProcessingException {
		//BitPayModel bitPayModel = null;
				
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(BitPayModel.class, new BitPayDeserializer());
		mapper.registerModule(module);

		return mapper.readValue(json, BitPayModel.class);
	}
		
}
