package com.april.task;

import com.april.task.controller.RatesMvcController;
import com.april.task.model.BitPayModel;
import com.april.task.model.ResponseResult;
import com.april.task.repository.RateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(MockitoJUnitRunner.class)
public class MockController {

	@Mock
    @Qualifier("namedParameterJdbcRepository")
    RateRepository repository;

    private MockMvc mvc;
    private BitPayModel model;
    
    @InjectMocks
    private RatesMvcController controller;

    private JacksonTester<BitPayModel> jsonBitPay;
    

    @Before
    public void setup() {
    	model = new BitPayModel("ETH", "EUR", "Euro", 100.9, new Date());
    	        
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
  
    
    @Test
    public void TestShowById() throws Exception {
    	   	
    	given(repository.getById(1)).willReturn(model);
    	
    	MockHttpServletResponse response = mvc.perform(
    			get("/getById?id=1")
                    .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    	assertNotNull(response);
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	
    	assertThat(response.getContentAsString()).isEqualTo(jsonBitPay.write(model).getJson() );
    }
    
    @Test
    public void TestShowEmptyList() throws Exception {
    	List<BitPayModel> list = new ArrayList<>();
    	given(repository.getLatestRates(10)).willReturn(list);
    	
    	MockHttpServletResponse response = mvc.perform(
                get("/showLatestRates?limit=1")
                    .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
    	
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    	assertThat(response.getContentAsString()).isEqualTo( "[]" );
    }

    @Test
    public void TestShowList() throws Exception {
    	model = new BitPayModel("ETH", "EUR", "Euro", 100.9, null);
    	
    	List<BitPayModel> list = new ArrayList<>();
    	list.add(model);
    	given(repository.getLatestRates(10)).willReturn(list);
    	
    	MockHttpServletResponse response = mvc.perform(
                get("/showLatestRates?limit=10")
                    .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
    	
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	    	
    	assertThat(response.getContentAsString()).isEqualTo( "[{\"fromCurrency\":\"ETH\",\"code\":\"EUR\",\"name\":\"Euro\",\"rate\":100.9,\"datePub\":null}]" );
    }

    
    @Test
    public void TestGetByIdWhenDoesNotExist() throws Exception {
        
        given(repository.getById(1))
                .willThrow(new EmptyResultDataAccessException(1));

        MockHttpServletResponse response = mvc.perform(
        		get("/getById?id=1")
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getContentAsString()).isEqualTo("\"Incorrect result size: expected 1, actual 0\"");
    }
    

    @Test
    public void TestSaveFreshRate() throws Exception {
    	ResponseResult responseResult = new ResponseResult(true, "that's good enough");
        given(repository.downloadRate()).willReturn(responseResult);
    	
    	MockHttpServletResponse response = mvc.perform(
    			get("/saveFreshRate")
                    .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    	assertNotNull(response);
    	assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    	
    	assertThat(response.getContentAsString()).isEqualTo("{\"status\":true,\"message\":\"that's good enough\"}");   	
    }
  
}
