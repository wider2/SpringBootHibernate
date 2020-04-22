package com.april.task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.april.task.model.ResponseResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MySpringBootApplication.class)
@WebAppConfiguration
public class MockWithContext {
 	
    @Autowired
    private WebApplicationContext webAppContext;
    
    private MockMvc mockMvc;
 
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }
     
    
    @Test
    public void getRatesList() throws Exception {
       
       MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/showLatestRates?limit=10")
          .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
              
       assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
       
       String content = mvcResult.getResponse().getContentAsString();
       assertNotNull(content);
       assertEquals("\"No BitPay records found in database.\"", content);
    }
        
    
    @Test
    public void testSaveFreshRate() throws Exception {
    	ResponseResult responseResult = new ResponseResult(true, "OK");
    	
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/saveFreshRate")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
        assertEquals("{\"status\":true,\"message\":\"OK\"}", content);
    }
    
}