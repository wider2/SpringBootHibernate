package com.april.task.repository;

import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.april.task.exception.RecordNotFoundException;
import com.april.task.model.BitPayModel;
import com.april.task.model.ResponseResult;
import com.april.task.util.Utility;

@Repository
public class RateRepository implements RateInterface {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int addRate(BitPayModel model) {
    	
		return jdbcTemplate.update("INSERT INTO BITPAY (from_currency, code, name, rate, date_pub) values(?, ?, ?, ?, ?)",
				new Object[] {model.getFromCurrency(), model.getCode(), model.getName(), model.getRate(), model.getDatePub() });
	}
    
    /*
     * API request like this 
     * https://bitpay.com/api/rates/BTC/EUR
     */
    @Override
    public ResponseResult downloadRate() {
    	int resultRecord = 0;
    	String fromCurrency = "BTC";
    	String toCurrency = "EUR";
    	String url = String.format("https://bitpay.com/api/rates/%s/%s", fromCurrency, toCurrency);
    	ResponseResult responseResult = new ResponseResult();
    	
    	try {
	        RestTemplate restTemplate = new RestTemplate();        

	        ResponseEntity<BitPayModel> responseEntity = restTemplate.exchange(
	        		  url,
	        		  HttpMethod.GET,
	        		  null,
	        		  new ParameterizedTypeReference<BitPayModel>(){});
	        
	        if (responseEntity != null && responseEntity.hasBody() && responseEntity.getBody() != null) {
	        	        
	        	BitPayModel bitPayResponse = responseEntity.getBody();
		        logger.info("GET result: -> {}", bitPayResponse.getCode() + ", " + bitPayResponse.getName() + ", " + bitPayResponse.getRate());
		        
		        		        
		        if (!Utility.isModelValidated(bitPayResponse)) {
		        	logger.info("Wrong data found.");
		        } else {	        
		        	bitPayResponse.setFromCurrency(fromCurrency);
		        	bitPayResponse.setDatePub(new Date());
		            resultRecord = addRate(bitPayResponse);
	                logger.info("Record added: -> {}", resultRecord);
		        }
		        responseResult.setStatus(resultRecord == 1);
		        responseResult.setMessage("OK");	        
	        } else {
	        	logger.error("Remote resource data not found.");
				responseResult.setStatus(false);
				responseResult.setMessage("Resource data not found.");
	        }
	        
		} catch (Exception ex) {
			logger.error("getFreshRate Exception: -> {}", ex.getMessage());
			responseResult.setStatus(false);
			responseResult.setMessage(ex.getMessage());
    	}
    	return responseResult;
    }

    
    @Override
    public int getCountTotalRecords() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BITPAY", Integer.class);
    }

    @Override
    public List<BitPayModel> getLatestRates(long limit) throws RecordNotFoundException {
    	List<BitPayModel> result = jdbcTemplate.query("SELECT * FROM BITPAY ORDER BY date_pub DESC LIMIT ?", new Object[] { limit },
				new BeanPropertyRowMapper<BitPayModel>(BitPayModel.class));
    	
    	if (!result.isEmpty()) {
            return result;
        } else {
            throw new RecordNotFoundException("No BitPay records found in database.");
        }    	
	}
    
    
    @Override
    public BitPayModel getById(long id) throws RecordNotFoundException {
    	
    	List<BitPayModel> result = jdbcTemplate.query("SELECT * FROM BITPAY WHERE id=?", new Object[] { id },
				new BeanPropertyRowMapper<BitPayModel>(BitPayModel.class));
       
    	if (!result.isEmpty()) {
            return result.get(0);
        } else {
            throw new RecordNotFoundException("No BitPay record found for such id = " + id);
        }
    }
    
}
