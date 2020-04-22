package com.april.task.model;

import java.util.Date;
import javax.validation.constraints.NotBlank;

public class BitPayModel {

    private Long id;

    @NotBlank(message = "From currency is mandatory")
    private String fromCurrency;
    
    @NotBlank(message = "Code is mandatory")
    private String code;
    
    @NotBlank(message = "Name is mandatory")
    private String name;
    
    private Double rate;
    private Date datePub;   

    public BitPayModel() {
    	super();
	}

    public BitPayModel(String fromCurrency, String code, String name, Double rate, Date datePub) {
    	super();
    	this.fromCurrency = fromCurrency;
    	this.code = code;
    	this.name = name;
    	this.rate = rate;
    	this.datePub = datePub;
	}

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getDatePub() {
        return datePub;	
    }
    
    public void setDatePub(Date datePub) {
        this.datePub = datePub;	
    }
    
    @Override
    public String toString() {
        return "BitPayModel{" +
                "id=" + id +
                ", fromCurrency='" + fromCurrency + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", datePub='" + datePub + '\'' +
                '}';
    }
    
    
}
