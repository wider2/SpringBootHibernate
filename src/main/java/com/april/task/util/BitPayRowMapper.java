package com.april.task.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.april.task.model.BitPayModel;


class BitPayRowMapper implements RowMapper<BitPayModel> {

	@Override
	public BitPayModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BitPayModel model = new BitPayModel();
		model.setFromCurrency(rs.getString("from_currency"));
		model.setCode(rs.getString("code"));
		model.setName(rs.getString("name"));
		model.setRate(rs.getDouble("rate"));
		model.setDatePub(rs.getDate("date_pub"));
		return model;
	}

}	