package com.april.task.repository;

import java.util.List;
import java.util.Optional;

import com.april.task.exception.RecordNotFoundException;
import com.april.task.model.BitPayModel;
import com.april.task.model.ResponseResult;

public interface RateInterface {

	int addRate(BitPayModel model);
	
	ResponseResult downloadRate();
	
	int getCountTotalRecords();
	
	List<BitPayModel> getLatestRates(long limit) throws RecordNotFoundException;
	
	//Optional<BitPayModel> getById(long id) throws RecordNotFoundException;
	BitPayModel getById(long id) throws RecordNotFoundException;
}