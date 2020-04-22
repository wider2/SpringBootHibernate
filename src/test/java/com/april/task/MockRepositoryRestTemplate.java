package com.april.task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.april.task.exception.RecordNotFoundException;
import com.april.task.model.BitPayModel;
import com.april.task.model.ResponseResult;
import com.april.task.repository.RateRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MockRepositoryRestTemplate {

	@Mock
    @Qualifier("namedParameterJdbcRepository")
    private RateRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    private BitPayModel model;
    
    @Before
    public void setup() {
    	model = new BitPayModel("ETH", "EUR", "Euro", 100.9, new Date());
    }

    
    @Test
    public void TestSaveFreshRate() {
    	ResponseResult responseResult = new ResponseResult(true, "OK");
    	given(repository.downloadRate()).willReturn(responseResult);
    	
    	ResponseEntity<ResponseResult> response = restTemplate.getForEntity("/saveFreshRate", ResponseResult.class);
    	
    	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    	assertTrue(responseResult.getStatus());
    }
    	
    
    @Test
    public void TestShowItems() throws RecordNotFoundException {
    	try {
	        List<BitPayModel> list = new ArrayList<>();
	        list.add(model);
	        given(repository.getLatestRates(10)).willReturn(list);
    	    
	        ResponseEntity<String> response = restTemplate.getForEntity("/showLatestRates?limit=10", String.class);
    	    
	        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	        
    	} catch (RecordNotFoundException ex) {
            assertEquals("No BitPay records found in database.", ex.getMessage());
    	}
    }
    
}