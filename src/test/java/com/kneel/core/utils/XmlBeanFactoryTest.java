package com.kneel.core.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.kneel.core.domain.PlmPropertiesDO;

public class XmlBeanFactoryTest  extends TestCase {

	public void testBean(){
//		ClassPathResource resource = new ClassPathResource("spring-beans.xml");
		//XmlBeanFactory factory = new XmlBeanFactory(resource);//it is deprecated, but easy to learn, also you can use it as below
		
//		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();//声明Factory
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);//BeanDefinition定义读取 
//		reader.loadBeanDefinitions(resource);//从resource中读取BeanDefinition到Factory.
		//fail
		
		ApplicationContext factory = new ClassPathXmlApplicationContext("spring-beans.xml");
		
		JdbcTemplate template = (JdbcTemplate) factory.getBean("jdbcTemplate");
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
}
