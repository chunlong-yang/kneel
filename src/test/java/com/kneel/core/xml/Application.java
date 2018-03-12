package com.kneel.core.xml;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import  org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@Configuration
//@PropertySource("config.properties")
//this will be override placeholder
@ImportResource("spring-beans.xml")
@ComponentScan("com.kneel.core.dao")
public class Application {

	@Value("${jdbc.url}")
	private String dburl;
	 
	
	@Autowired
	private Environment env;

	public String getDburl() {
		return dburl;
	}
	
	public String getUser(){
		return env.getProperty("jdbc.username");
	}

	@Bean
	public Map<String,String> props(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("jdbc.username", getUser());
		map.put("jdbc.url", getDburl());
		return map;
	}
	
	
}
