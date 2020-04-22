package com.april.task;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.april.task.model.BitPayModel;
import com.april.task.model.ResponseResult;
import com.april.task.util.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestUtility {

	private String fromCurrency = "BTC";
	private String toCurrency = "EUR";
	private String currencyName = "EUR, Eurozone";
	private BitPayModel model;
	
	@Before
	public void setup() throws Exception {
		model = new BitPayModel(fromCurrency, toCurrency, currencyName, 4657.3, new Date());
	}

	@Test
	public void testNumericRight() {
		
		assertTrue(Utility.isNumericValue("6440"));
		
		assertTrue(Utility.isNumericValue("6440.99"));
		
		assertTrue(Utility.isNumericValue("-644.99"));
		
		assertTrue(Utility.isNumericValue("000"));
	}

	@Test
	public void testNumericWrong() {
		assertFalse(Utility.isNumericValue("6440,01"));	
		
		assertFalse(Utility.isNumericValue("6440 99"));
		
		assertFalse(Utility.isNumericValue(""));
		
		assertFalse(Utility.isNumericValue(null));
	}
	
	@Test
	public void testJsonConvertation() throws JsonProcessingException {
				
		assertTrue(Utility.isModelValidated(model));
					
		String jsonResult = Utility.convertToJson(model);
		assertNotNull(jsonResult);
		
		BitPayModel result = Utility.deserializeBitPay(jsonResult);
		assertEquals(model.getCode(), result.getCode());
	}

	@Test
	public void testSerializeList() throws JsonProcessingException {
		
		BitPayModel model2 = new BitPayModel(fromCurrency, toCurrency, currencyName, -4657.0, new Date());
		
		List<BitPayModel> list = new ArrayList<>();
		list.add(model);
		list.add(model2);
					
		String result = Utility.convertToJson(list);
		assertNotNull(result);
	}

	@Test
	public void testJson() throws JsonProcessingException {
		
		ResponseResult responseResult = new ResponseResult(true, "Ешкин кот");
		
		String result = Utility.convertToJson(responseResult);
		assertNotNull(result);
		assertThat(result, equalTo("{\"status\":true,\"message\":\"Ешкин кот\"}"));
	}
		
}
