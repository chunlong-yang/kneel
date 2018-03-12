package com.kneel.core.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kneel.core.domain.PlmPropertiesDO;

@Repository
public class PlmPropertiesDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public PlmPropertiesDO getMaxThreads(){
		PlmPropertiesDO property = jdbcTemplate.queryForObject("SELECT * FROM plm_properties WHERE property='maxThreads' and env='DEV1'", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
		return property;
	}
}
