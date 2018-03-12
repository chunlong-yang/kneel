package com.kneel.core.h2.jpa.entity;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

public interface PlmPropertiesRepository extends CrudRepository<PlmPropertiesEntity,Long>{

	@Query(value = "SELECT p FROM PlmPropertiesEntity p WHERE p.property = ? and p.env='DEV1'", nativeQuery = false)
	PlmPropertiesEntity findByProperty(String property);
	
	
	@Query(value = "SELECT * FROM plm_properties WHERE propcategory = :propcategory and env='DEV1'", nativeQuery = true)
	List<PlmPropertiesEntity> findByPropcategory(@Param("propcategory") String propcategory);
}
