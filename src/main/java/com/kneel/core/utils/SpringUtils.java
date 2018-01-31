/*
 * Created on Feb 15, 2007
 *
 * This software is the copyrighted, proprietary property of State
 * Street Corporation and its subsidiaries and affiliates
 * which retain all right, title and interest therein.
 */
package com.kneel.core.utils;

import java.util.Arrays;
 
import com.kneel.core.exception.KneelCoreException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 

/**
 * SpringUtil
 * 
 * call getContext method will be return ApplicationContext.
 * 
 * @author e557400
 * @since  Mar 10, 2016
 *
 */
public class SpringUtils {
	
	private static Log logger = LogFactory.getLog(SpringUtils.class);
	 
	private static ApplicationContext appCtx;
	
	private static ApplicationContext sevCtx;
	
	private static ApplicationContext webCtx;

	private static String[] appXmls = { "spring-report-beans.xml" };
	
	private static String[] sevXmls = { "spring-service-beans.xml" };
	
	private static String[] webXmls = { "spring-web-beans.xml" };

	/**
	 * get app configure file context.
	 * @return
	 */
	public static ApplicationContext getContext() throws KneelCoreException {
		try{
			if (appCtx == null) {
				appCtx = new ClassPathXmlApplicationContext(appXmls);
			}
		}catch(Exception e){
			String errorMessage = "Could not load TMV Spring Application Context file:" + Arrays.toString(appXmls);
			logger.error(errorMessage);
			throw new KneelCoreException(errorMessage,e);
		}
		return appCtx;
	}
	
	public static ApplicationContext getSevContext() throws KneelCoreException {
		try{
			if (sevCtx == null) {
				sevCtx = new ClassPathXmlApplicationContext(sevXmls);
			}
		}catch(Exception e){
			String errorMessage = "Could not load TMV Spring Application Context file:" + Arrays.toString(sevXmls);
			logger.error(errorMessage);
			throw new KneelCoreException(errorMessage,e);
		}
		return sevCtx;
	}
	
	/**
	 * get web configure file context.
	 * @return
	 */
	public static ApplicationContext getWebContext() throws KneelCoreException {
		try{
			if (webCtx == null) {
				webCtx = new ClassPathXmlApplicationContext(webXmls);
			}
		}catch(Exception e){
			String errorMessage = "Could not load TMV Spring Application Context file:" + Arrays.toString(webXmls);
			logger.error(errorMessage);
			throw new KneelCoreException(errorMessage,e);
		}
		return webCtx;
	}
	
}
