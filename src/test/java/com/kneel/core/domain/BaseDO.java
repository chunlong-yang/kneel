package com.kneel.core.domain;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.SQLException;
 

/**
 * base do, give public method.
 * 
 * @author e557400
 *
 */ 
@SuppressWarnings("rawtypes")
public class BaseDO implements Comparable{
	
	private String order;
	 
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	/**
     * Returns a PropertyDescriptor[] for the given Class.
     *
     * @param c The Class to retrieve PropertyDescriptors for.
     * @return A PropertyDescriptor[] describing the Class.
     * @throws SQLException if introspection failed.
     */
	public PropertyDescriptor[] propertyDescriptors(Class<?> c)
	        throws SQLException {
	        // Introspector caches BeanInfo classes for better performance
	        BeanInfo beanInfo = null;
	        try {
	            beanInfo = Introspector.getBeanInfo(c);

	        } catch (IntrospectionException e) {
	            throw new SQLException(
	                "Bean introspection failed: " + e.getMessage());
	        }

	        return beanInfo.getPropertyDescriptors();
	}
	
	@Override
	public String toString(){ 
		StringBuffer sb = new StringBuffer(this.getClass().getSimpleName());
		sb.append("{").append("\n");
		try {
			PropertyDescriptor[] props = propertyDescriptors(this.getClass());
			for(PropertyDescriptor prop:props){
				if("class".equals(prop.getName())||"order".equals(prop.getName())){
					continue;
				}
				sb.append(prop.getName());
				sb.append(":");
				sb.append(prop.getReadMethod().invoke(this));
				sb.append("\n");
			}
		} catch (Exception e) { 
			sb.append("error"+e.getStackTrace());
		}  
		sb.append("}").append("\n");
		return sb.toString(); 
		
	}

	@Override
	public int compareTo(Object o) {
		BaseDO order = (BaseDO)o;
		return this.getOrder().hashCode()-order.getOrder().hashCode();
	}
	 
}

