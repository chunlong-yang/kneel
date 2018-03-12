package com.kneel.core.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kneel.core.dao.PlmPropertiesDAO;
import com.kneel.core.derby.SpringDerbyBaseTest;

public class PlmPropertiesDAODerbyTest extends SpringDerbyBaseTest{// SpringBaseTest {
	
	@Autowired
	private PlmPropertiesDAO plmPropertiesDAO;
	
	@Test
	public void testQuery(){
		System.out.println(plmPropertiesDAO.getMaxThreads());
	}
}
