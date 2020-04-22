package com.april.task.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.april.task.exception.RecordNotFoundException;
import com.april.task.model.BitPayModel;


@Repository
public class NamedParameterJdbcRepository extends RateRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public int addRate(BitPayModel model) {
        return namedParameterJdbcTemplate.update(
                "INSERT INTO BITPAY (from_currency, code, name, rate, date_pub) VALUES (:fromCurrency, :code, :name, :rate, :datePub)",
                new BeanPropertySqlParameterSource(model));
    }

    @Override
    public BitPayModel getById(long id) throws RecordNotFoundException {
    	return namedParameterJdbcTemplate.queryForObject(
    			"SELECT * FROM BITPAY WHERE id = :id",
    			new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                new BitPayModel(
                        rs.getString("from_currency"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getDouble("rate"),
                        rs.getDate("date_pub")
                )
    			);
    }
    
}
