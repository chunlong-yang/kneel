package com.kneel.core.xml;

import java.util.Map;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kneel.core.dao.PlmPropertiesDAO;
import com.kneel.core.domain.PlmPropertiesDO;

public class ApplicationTest  extends TestCase {

	public void testApplication(){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		JdbcTemplate template = (JdbcTemplate)ctx.getBean("jdbcTemplate");
		PlmPropertiesDO property = template.queryForObject("select  propertyid, property, value,propcategory,env from plm_properties where  property='maxThreads' and env='DEV1'", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
		System.out.println(property);
		
		
		ctx.close();
	}
	
	public void testApplicationPropertyResource(){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		Map<String,String>  map = (Map<String, String>) ctx.getBean("props");//props
		System.out.println(map); 
		
		ctx.close();
	}
	 
	
	
	public void testRepository() throws Exception{
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		PlmPropertiesDAO dao = (PlmPropertiesDAO)ctx.getBean("plmPropertiesDAO");
		System.out.println(dao.getMaxThreads());
	}
	
	public void testApplicationBean() throws Exception{
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		BeanFactory factory = (BeanFactory)ctx.getBean("application");
		Object bean = factory.getBean("org.springframework.context.event.internalEventListenerFactory");
		System.out.println(bean);
		Object bean1 = factory.getBean("plmPropertiesDAO");
		System.out.println(bean1); 
	}
	 
}
