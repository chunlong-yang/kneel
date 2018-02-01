package com.kneel.core.domain;


/**
 * PlmPropertiesDO is generator by robot, don't make any changes
 * 
 */
public class PlmPropertiesDO extends BaseDO{

	//propertyid
	private Long propertyid;
	
	//property
	private String property;
	
	//value
	private String value;
	
	//env
	private String env;
	
	//propcategory
	private String propcategory;
	
	//override
	private String override;
	
	//priority
	private Long priority;
	

	public Long getPropertyid(){
		return propertyid;
	}
	
	public void setPropertyid(Long propertyid){
		this.propertyid = propertyid;
	}
	
	public String getProperty(){
		return property;
	}
	
	public void setProperty(String property){
		this.property = property;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public String getEnv(){
		return env;
	}
	
	public void setEnv(String env){
		this.env = env;
	}
	
	public String getPropcategory(){
		return propcategory;
	}
	
	public void setPropcategory(String propcategory){
		this.propcategory = propcategory;
	}
	
	public String getOverride(){
		return override;
	}
	
	public void setOverride(String override){
		this.override = override;
	}
	
	public Long getPriority(){
		return priority;
	}
	
	public void setPriority(Long priority){
		this.priority = priority;
	}
	
}

