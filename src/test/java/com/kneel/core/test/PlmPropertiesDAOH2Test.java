package com.kneel.core.test;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.h2.tools.Console;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.kneel.core.dao.PlmPropertiesDAO;
import com.kneel.core.h2.SpringH2BaseTest;
import com.kneel.core.h2.jpa.entity.PlmPropertiesEntity;
import com.kneel.core.h2.jpa.entity.PlmPropertiesRepository;

public class PlmPropertiesDAOH2Test extends SpringH2BaseTest{// SpringBaseTest {
	
	@Autowired
	private PlmPropertiesDAO plmPropertiesDAO;
	
	@Test
	public void testQuery(){
		System.out.println(plmPropertiesDAO.getMaxThreads());
	}
	 
	@Autowired
	private PlmPropertiesRepository plmPropertiesRepository;

	@Test
	public void testJap(){
		Iterator<PlmPropertiesEntity> it = plmPropertiesRepository.findAll().iterator(); 
		while(it.hasNext()){
			PlmPropertiesEntity po = it.next();
			System.out.println(po.getPropertyid());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	}
	
	@Test
	public void testQueryAn(){
		PlmPropertiesEntity po = plmPropertiesRepository.findByProperty("maxThreads");
		System.out.println(po.getPropertyid());
		System.out.println(po.getProperty());
		System.out.println(po.getValue());
	}
	
	@Test
	public void testQueryList(){
		List<PlmPropertiesEntity> pos = plmPropertiesRepository.findByPropcategory("archive");
		for(PlmPropertiesEntity po:pos){
			System.out.println(po.getPropertyid());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
		
	}
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Test
	public void testNormalJap(){
		EntityManager em = entityManagerFactory.createEntityManager();
		PlmPropertiesEntity po = em.find(PlmPropertiesEntity.class, 1L);
		System.out.println(po.getPropertyid());
		System.out.println(po.getProperty());
		System.out.println(po.getValue());
	}
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	public void testNolJap(){
		PlmPropertiesEntity po = entityManager.find(PlmPropertiesEntity.class, 1L);
		System.out.println(po.getPropertyid());
		System.out.println(po.getProperty());
		System.out.println(po.getValue());
	}
}
