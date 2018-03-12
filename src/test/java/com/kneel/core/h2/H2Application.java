package com.kneel.core.h2;

import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.h2.tools.Console;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@PropertySource("derby-config.properties")
@ComponentScan({ "com.kneel.core.dao","com.kneel.core.h2.jpa.entity" })
@EnableJpaRepositories("com.kneel.core.h2.jpa.entity")
public class H2Application {

	/**
	 * memory model
	 * 
	 * DriverClass: org.h2.Driver Url:
	 * jdbc:h2:mem:h2db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false Username: sa
	 * Password:
	 * 
	 * @return
	 */
	// @Bean
	// public DataSource dataSource() {
	// EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
	// EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
	// .setName("h2db")
	// .addScript("DML/derby/schema.sql")
	// .addScript("DML/derby/plm_properties.sql")
	// .build();
	// return db;
	// }

	/**
	 * Local model
	 * 
	 * @return
	 */
	// @Bean
	// public DataSource dataSource(){
	// SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
	// dataSource.setDriver(new org.h2.Driver());
	// dataSource.setUrl("jdbc:h2:~/h2db");
	// dataSource.setUsername("admin");
	// dataSource.setPassword("");
	//
	// //remember just init first time.
	// // ResourceDatabasePopulator databasePopulator = new
	// ResourceDatabasePopulator();
	// // ResourceLoader resourceLoader = new DefaultResourceLoader();
	// //
	// databasePopulator.addScript(resourceLoader.getResource("DML/derby/schema.sql"));
	// //
	// databasePopulator.addScript(resourceLoader.getResource("DML/derby/plm_properties.sql"));
	// // DatabasePopulatorUtils.execute(databasePopulator, dataSource);
	//
	// return dataSource;
	// }

	/**
	 * you don't need to start server in Bean, you can start outside
	 */
	// @Bean(destroyMethod="stop")
	// public Server server(){
	// Server server = new Server();
	// try {
	// server = Server.createTcpServer(new String
	// []{"-tcp","-tcpAllowOthers","-tcpPort","8043"});
	// server.start();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// System.out.println("===============================>create server fail!");
	// }
	// System.out.println("===============================>create server success!");
	// return server;
	// }

	/**
	 * you don't need to start console, if you want you can start in testCase.
	 * 
	 * @return
	 */
	// @Bean(destroyMethod="shutdown")
	// public Console console(){
	// Console console = new Console();
	// try {
	// console.runTool(new String[]{});
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// System.out.println("===============================>init Server Console fail!");
	// }
	// System.out.println("===============================>init Server Console success!");
	// return console;
	// }

	/**
	 * Network model
	 * 
	 * @return
	 */
	@Bean
	// @DependsOn("server")
	public DataSource dataSource() {

		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriver(new org.h2.Driver());
		dataSource.setUrl("jdbc:h2:tcp://localhost:8043/~/h2db");
		dataSource.setUsername("admin");
		dataSource.setPassword("");

		// remember just init first time.
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		// databasePopulator.addScript(resourceLoader.getResource("DML/derby/schema.sql"));
		// databasePopulator.addScript(resourceLoader.getResource("DML/derby/plm_properties.sql"));
		DatabasePopulatorUtils.execute(databasePopulator, dataSource);

		return dataSource;

	}

	/**
	 * JPA H2 adapter.
	 * 
	 * @return
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
		bean.setDatabase(Database.H2);
		//bean.setGenerateDdl(true);
		return bean;
	}
	
	/**
	 * JPA Entity Manager
	 * 
	 * @param dataSource
	 * @param jpaVendorAdapter
	 * @return
	 */
	@Bean 
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory( 
			DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) { 
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean(); 
		bean.setDataSource(dataSource); 
		bean.setJpaVendorAdapter(jpaVendorAdapter); 
		bean.setPackagesToScan("com.kneel.core.h2.jpa.entity"); 
		return bean; 
	} 

	
	
	
	/** 
	 * JPA Transaction Manager,via Spring can use it.
	 * @param emf
	 * @return
	 */
	@Bean 
	@Autowired
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) { 
		return new JpaTransactionManager(emf); 
	} 


	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
}
