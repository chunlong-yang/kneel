package com.kneel.core.derby;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@PropertySource("derby-config.properties")
@ComponentScan({"com.kneel.core.dao"})
public class DerbyApplication {

	/**
	 * memory model
	 * 
	 * DriverClass: EmbeddedDriver.class
	 * Url:         jdbc:derby:memory:%s;%s.
	 * Username:    sa
	 * Password:
	 * 
	 * @return
	 */
//	@Bean
//	public DataSource dataSource() { 
//		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.DERBY)
//				.setName("testdb")
//				.addScript("DML/derby/schema.sql")
//				.addScript("DML/derby/plm_properties.sql")
//				.build();
//		return db;
//	}
	
	/**
	 * Local model
	 * 
	 * @return
	 */
//	@Bean
//	public DataSource dataSource(){
//		SimpleDriverDataSource dataSource = new SimpleDriverDataSource(); 
//		dataSource.setDriver(new org.apache.derby.jdbc.EmbeddedDriver());
//		dataSource.setUrl("jdbc:derby:testdb;create=true");
//		dataSource.setUsername("sa");
//		dataSource.setPassword(""); 
//		
//		//remember just init first time.
////		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
////		ResourceLoader resourceLoader = new DefaultResourceLoader();
////		databasePopulator.addScript(resourceLoader.getResource("DML/derby/schema.sql"));
////		databasePopulator.addScript(resourceLoader.getResource("DML/derby/plm_properties.sql"));
////		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
//		
//		return dataSource;
//	}
	
	
	/**
	 * Network model
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource(){
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new org.apache.derby.jdbc.ClientDriver());
        dataSource.setUrl("jdbc:derby://localhost:1527/testdb;create=true");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin"); 
        
        // remember just init first time.
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		//databasePopulator.addScript(resourceLoader.getResource("DML/derby/schema.sql"));
		//databasePopulator.addScript(resourceLoader.getResource("DML/derby/plm_properties.sql"));
		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        
        return dataSource;

	}
	
	
	
	@Bean
	public JdbcTemplate jdbcTemplate(){
		return new JdbcTemplate(dataSource());
	}
}
