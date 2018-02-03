package com.kneel.core.utils;
 
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.kneel.core.domain.PlmPropertiesDO;

/**
 * 
 * JdbcTemplate: 核心JDBC操作模板.
 *  1. query: 封装JDBC的query对象列表操作.(只需要传递SQL,Bean暖水瓶能自动实现查询到对象的操作)
 *  2. queryForObject 封装JDBC的query对象操作.(只需要传递SQL,Bean暖水瓶能自动实现查询到对象的操作)
 * 
 * tools
 *  1. SimpleJdbcInsert 封装JDBC的Insert操作. (只需要指定表名, Bean就能自动实现insert操作)
 *  2. SimpleJdbcCall   封装JDBC的 Function,Store Procedure的操作 (只需要指定函数名, Map就能自动实现调用).
 * 
 * @author Administrator
 *
 */
public class JdbcTemplateTest extends TestCase{

	public void testDb(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate)context.getBean("jdbcTemplate");
		PlmPropertiesDO property = template.queryForObject("select rid as propertyid,propkey as property, propvalue as value,propcategory,env from base_properties where rid = 41", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
		System.out.println(property);
	}
	
	public void testIn(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate)context.getBean("jdbcTemplate");
		 
		PlmPropertiesDO property = template.queryForObject("select rid as propertyid,propkey as property, propvalue as value,propcategory,env from base_properties where rid = 41", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
		
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		insert.withTableName("base_properties");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("rid", template.queryForObject("select base_properties_seq.nextval from dual",Long.class));
		args.put("propkey", property.getProperty());
		args.put("propvalue", property.getValue());
		args.put("propcategory", property.getPropcategory());
		args.put("env", property.getEnv());
		int effect = insert.execute(args);
		System.out.println(effect);
	}
	
	public void testInBean(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate)context.getBean("jdbcTemplate");
		PlmPropertiesDO property = new PlmPropertiesDO();
		property.setPropertyid(template.queryForObject("select plm_properties_seq.nextval from dual",Long.class));
		property.setEnv("dev"); 
		property.setPropcategory("log4j");
		property.setProperty("log4j.rootLogger");
		property.setValue("ERROR, ROOT");
		property.setOverride("default");
		property.setPriority(10L);
		  
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		insert.withTableName("plm_properties"); 
		int effect = insert.execute(new BeanPropertySqlParameterSource(property));//same column and bean properties.
		System.out.println(effect);
	}
	
	public void testFunction(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate)context.getBean("jdbcTemplate");
		SimpleJdbcCall call = new SimpleJdbcCall(template);
		call.withFunctionName("plm_getdate");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("key", "ignore");
		String date = call.executeFunction(String.class, args); 
		System.out.println(date);
	}
}
