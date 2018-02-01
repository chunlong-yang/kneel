package com.kneel.core.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.kneel.core.domain.PlmPropertiesDO;

public class SpringUtilsTest  extends TestCase {
 
	/**
	 * execute normal SQL, and write RowMapper to set Columns to POJO's Properties.
	 */
	public void testSpringUtilsSQLRowMapper(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate) context.getBean("jdbcTemplate");
		List<PlmPropertiesDO> properties = template.query("SELECT * FROM plm_properties WHERE property='maxThreads'", new RowMapper<PlmPropertiesDO>(){
			@Override
			public PlmPropertiesDO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PlmPropertiesDO property = new PlmPropertiesDO();
				property.setEnv(rs.getString("env"));
				//property.setOrder(rs.getString("order"));
				property.setOverride(rs.getString("override"));
				property.setPriority(rs.getLong("priority"));
				property.setPropcategory(rs.getString("propcategory"));
				property.setProperty(rs.getString("property"));
				property.setPropertyid(rs.getLong("propertyid"));
				property.setValue(rs.getString("value"));
				return property;
			}
			
		});
		
		System.out.println(properties);
	}
	
	/**
	 * execute normal SQL, and use Spring BeanPropertyRowmapper, auto set Columns to POJO's Properties.
	 *   
	 */
	public void testBeanPropertyRowMapper(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate) context.getBean("jdbcTemplate");
		List<PlmPropertiesDO> properties = template.query("SELECT * FROM plm_properties WHERE property='maxThreads'", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
		System.out.println(properties);
	}
	
	/**
	 * execute normal SQL, and use Spring BeanPropertyRowmapper, auto set Columns to POJO's Properties.
	 * 
	 */
	public void testBeanPropertyRowMapperOnlyOne(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate) context.getBean("jdbcTemplate");
		PlmPropertiesDO properties = template.queryForObject("SELECT * FROM plm_properties WHERE property='maxThreads' and env='DEV1'", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
		System.out.println(properties);
	}
	
	public void testTransaction(){
		final ApplicationContext context = SpringUtils.getContext();
		TransactionTemplate template = (TransactionTemplate) context.getBean("tranTemplate");
		
		
		Integer bulk = template.execute(new TransactionCallback<Integer>(){
			@Override
			public Integer doInTransaction(TransactionStatus status) {
				updateOperator1();
				updateOperator2();
				return 1;
			}

			private void updateOperator2() { 
				JdbcTemplate jdbctemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
				SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbctemplate);
				// this is not enough, we should be create sql as INSERT INTO plm_properties(PROPERTYID,PROPERTY,VALUE,ENV,PROPCATEGORY,OVERRIDE,PRIORITY) VALUES(seq.nextval,?,?,?,?,?,?)
				insert.withTableName("plm_properties"); 
				// can use spring BeanPropertyRowMapper to get all object properties and return map of all properties.
				Map<String,Object> args = new HashMap<String,Object>(); 
				insert.execute(args);
				// if we all do this step, why we need SimpleJdbcInsert.
			
				//jdcbtemplate.execute("INSERT INTO plm_properties(PROPERTYID,PROPERTY,VALUE,ENV,PROPCATEGORY,OVERRIDE,PRIORITY) VALUES(?,?,?,?,?,?,?)"); 
				
			}

			private void updateOperator1() {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		System.out.println(bulk);
	}
}
