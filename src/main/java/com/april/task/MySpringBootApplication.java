package com.april.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;

import com.april.task.model.ResponseResult;
import com.april.task.repository.RateRepository;

@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class MySpringBootApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    @Qualifier("namedParameterJdbcRepository")
	RateRepository rateRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(MySpringBootApplication.class, args);
				
	}

    @Override
    public void run(String... args) throws Exception 
    {    	
		final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				ResponseResult result = rateRepository.downloadRate();
			    if (!result.getStatus()) {
					logger.info("Total: -> {}", rateRepository.getCountTotalRecords());
					
			    }
			}			
		}, 0, 60, TimeUnit.SECONDS);
    }
    
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return "error-404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return "error-500";
	        }
	    }
	    return "error";
	}
	
}
