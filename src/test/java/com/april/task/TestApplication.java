package com.april.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.april.task.exception.RecordNotFoundException;
import com.april.task.model.BitPayModel;
import com.april.task.model.ResponseResult;
import com.april.task.repository.RateRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestApplication {

	private String fromCurrency = "ETH";
	private String toCurrency = "USD";
	private String currencyName = "US Dollar";
	  
    @Autowired
    @Qualifier("namedParameterJdbcRepository")
	RateRepository repository;
    
	@Before
	public void setup() throws Exception {
	
	}
		
	@Test
	public void testAddItemRate() throws Exception {
		BitPayModel model = new BitPayModel(fromCurrency, toCurrency, currencyName, 11.9, new Date());
		
		int result = repository.addRate(model);
		assertNotNull(result);
		assertEquals(1, result);
        assertThat(result, not(0));                
	}

	@Test
	public void testAddAnotherRate() throws Exception {
		BitPayModel model = new BitPayModel(fromCurrency, toCurrency, "", 0.0, new Date());
		int result = repository.addRate(model);
		assertEquals(1, result);
		
		BitPayModel response = repository.getById(1);
		assertNotEquals(response.getRate(), 0);
		
		//Optional<BitPayModel> response = repository.getById(1);
		//assertNotEquals(response.get().getRate(), 0);
	}
			
	@Test
	public void testShowBitPays() throws RecordNotFoundException {
		List<BitPayModel> list = (List<BitPayModel>) repository.getLatestRates(10);
		assertThat(list.size(), not(0));
		assertTrue(list.size() > 0);
	}	

	@Test
	public void testShowBitPaysNegative() throws RecordNotFoundException {
		try {
			List<BitPayModel> list = (List<BitPayModel>) repository.getLatestRates(0);
	        fail();
		} catch (Exception ex ) {
			assertEquals("No BitPay records found in database.", ex.getMessage());
		}
	}	

	@Test
	public void testCountItem() {
		int countResult = repository.getCountTotalRecords();
		
		assertThat(countResult, not(0));
		assertTrue(countResult > 0);
	}

	@Test
	public void testDownloadItem() {
		ResponseResult result = repository.downloadRate();
				
		assertTrue(result.getStatus());
		assertThat(result.getMessage(), equalTo("OK"));
	}
		
	@Test
	public void testGetByIdPositive() throws RecordNotFoundException{
		int id = 1;
		
	    BitPayModel result = repository.getById(id);
	    
	    assertNotNull(result);
		assertEquals(toCurrency, result.getCode());
	}

	//if value does not exist in database
	@Test
	public void testByIdNegative() throws RecordNotFoundException {
		int id = 100001;								
		try {
			BitPayModel result = repository.getById(id);
            fail();
		} catch (Exception ex ) {
			assertEquals("Incorrect result size: expected 1, actual 0", ex.getMessage());
		}
	}
	
}
