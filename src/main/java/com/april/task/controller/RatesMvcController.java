package com.april.task.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.april.task.model.BitPayModel;
import com.april.task.model.ResponseResult;
import com.april.task.repository.RateRepository;
import com.april.task.util.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
 

@Controller
public class RatesMvcController implements WebMvcConfigurer
{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("namedParameterJdbcRepository")
	RateRepository rateRepository;
	
	
	// BitPay API documentation
	//https://bitpay.com/api/#rest-api-resources-rates-fetch-the-rates-used-by-bitpay-for-a-specific-cryptocurrency

	@RequestMapping(value = "/saveFreshRate", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String saveFreshRates() throws JsonProcessingException {
		
		ResponseResult result = rateRepository.downloadRate();
		logger.info("saveFreshRate: -> {}", result.toString());
		
		return Utility.convertToJson(result);
	}
		

	@RequestMapping(value = "/getById", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getById(@RequestParam(name = "id", required = true, defaultValue = "0") String inputId) throws JsonProcessingException {
		String jsonResult = null;
    	try {
    		if(Utility.isNumericValue(inputId)) {
    			int id = Integer.parseInt(inputId);
    			BitPayModel model = rateRepository.getById(id);
                jsonResult = Utility.convertToJson(model);
    			
                /*
    			Optional<BitPayModel> optionalResult = rateRepository.getById(id);            
    			if(optionalResult.isPresent()) {
                    jsonResult = Utility.convertToJson(optionalResult.get());
        		} else {
        			ResponseResult responseResult = new ResponseResult(false, "There is no data found by input parameter " + id);
        			jsonResult = Utility.convertToJson(responseResult);
    			}
    			*/
    		} else {
    			ResponseResult responseResult = new ResponseResult(false, "Input parameter is incorrect.");
    			jsonResult = Utility.convertToJson(responseResult);
    		}
    	} catch (Exception ex) {
    		logger.error("getById Exception: -> {}", ex.getMessage() + ", " + ex.getStackTrace());
    		jsonResult = Utility.convertToJson(ex.getMessage());
    	}
    	return jsonResult;
	}
	
	
	@RequestMapping(value = "/showLatestRates", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getLatestRates(@RequestParam(name = "limit", required = false, defaultValue = "10") String inputLimit) throws JsonProcessingException
	{
		String jsonResult = null;
    	try {
    		if(Utility.isNumericValue(inputLimit)) {
    			int limit = Integer.parseInt(inputLimit);
                List<BitPayModel> list = rateRepository.getLatestRates(limit);
            
                jsonResult = Utility.convertToJson(list);
    		} else {
    			ResponseResult responseResult = new ResponseResult(false, "Input parameter is incorrect.");
    			jsonResult = Utility.convertToJson(responseResult);
    		}
		} catch (Exception ex) {
			logger.error("showLatestRates Exception: -> {}", ex.getMessage());
    		    		
			jsonResult = Utility.convertToJson(ex.getMessage());
		}
    	return jsonResult;
    }

}