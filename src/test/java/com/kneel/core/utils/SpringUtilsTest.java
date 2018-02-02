package com.kneel.core.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.kneel.core.domain.PlmPropertiesDO;

public class SpringUtilsTest  extends TestCase {
 
	/**
	 * execute normal SQL, and write RowMapper to set Columns to POJO's Properties.
	 */
	public void testSpringUtilsSQLRowMapper(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate) context.getBean("jdbcTemplate");
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
	
	/**
	 * execute normal SQL, and use Spring BeanPropertyRowmapper, auto set Columns to POJO's Properties.
	 *   
	 */
	public void testBeanPropertyRowMapper(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate) context.getBean("jdbcTemplate");
		List<PlmPropertiesDO> properties = template.query("SELECT * FROM plm_properties WHERE property='maxThreads'", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
		System.out.println(properties);
	}
	
	/**
	 * execute normal SQL, and use Spring BeanPropertyRowmapper, auto set Columns to POJO's Properties.
	 * 
	 */
	public void testBeanPropertyRowMapperOnlyOne(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate) context.getBean("jdbcTemplate");
		PlmPropertiesDO properties = template.queryForObject("SELECT * FROM plm_properties WHERE property='maxThreads' and env='DEV1'", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
		System.out.println(properties);
	}
	
	public void testTransaction(){
		final ApplicationContext context = SpringUtils.getContext();
		TransactionTemplate template = (TransactionTemplate) context.getBean("tranTemplate");
		
		
		Integer bulk = template.execute(new TransactionCallback<Integer>(){
			@Override
			public Integer doInTransaction(TransactionStatus status) {
				updateOperator1();
				updateOperator2();
				failOperator();
				return 1;
			}

			private void updateOperator2() { 
				JdbcTemplate jdbctemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
				SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbctemplate);
				// this is not enough, we should be create sql as INSERT INTO plm_properties(PROPERTYID,PROPERTY,VALUE,ENV,PROPCATEGORY,OVERRIDE,PRIORITY) VALUES(seq.nextval,?,?,?,?,?,?)
				insert.withTableName("plm_properties"); 
				// can use spring BeanPropertyRowMapper to get all object properties and return map of all properties.
				PlmPropertiesDO properties = jdbctemplate.queryForObject("SELECT * FROM plm_properties WHERE property='maxThreads' and env='DEV1'", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
				properties.setPropertyid(jdbctemplate.queryForObject("select plm_propertyidseq.nextval from dual", Long.class));
				properties.setProperty("ycl_abc2");
				
				insert.execute(new BeanPropertySqlParameterSource(properties));
				// if we all do this step, why we need SimpleJdbcInsert.
			
				//jdcbtemplate.execute("INSERT INTO plm_properties(PROPERTYID,PROPERTY,VALUE,ENV,PROPCATEGORY,OVERRIDE,PRIORITY) VALUES(?,?,?,?,?,?,?)"); 
				
			}

			private void updateOperator1() {
				// TODO Auto-generated method stub
				JdbcTemplate jdbctemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
				SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbctemplate);
				// this is not enough, we should be create sql as INSERT INTO plm_properties(PROPERTYID,PROPERTY,VALUE,ENV,PROPCATEGORY,OVERRIDE,PRIORITY) VALUES(seq.nextval,?,?,?,?,?,?)
				insert.withTableName("plm_properties"); 
				// can use spring BeanPropertyRowMapper to get all object properties and return map of all properties.
				PlmPropertiesDO properties = jdbctemplate.queryForObject("SELECT * FROM plm_properties WHERE property='maxThreads' and env='DEV1'", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
				properties.setPropertyid(jdbctemplate.queryForObject("select plm_propertyidseq.nextval from dual", Long.class));
				properties.setProperty("ycl_abc3");
				
				insert.execute(new BeanPropertySqlParameterSource(properties));
			}
			
			private void failOperator(){
				JdbcTemplate jdbctemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
				SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbctemplate);
				// this is not enough, we should be create sql as INSERT INTO plm_properties(PROPERTYID,PROPERTY,VALUE,ENV,PROPCATEGORY,OVERRIDE,PRIORITY) VALUES(seq.nextval,?,?,?,?,?,?)
				insert.withTableName("plm_properties"); 
				// can use spring BeanPropertyRowMapper to get all object properties and return map of all properties.
				PlmPropertiesDO properties = jdbctemplate.queryForObject("SELECT * FROM plm_properties WHERE property='maxThreads' and env='DEV1'", new BeanPropertyRowMapper<PlmPropertiesDO>(PlmPropertiesDO.class));
				properties.setPropertyid(9879L);//property id is exists, and it is the primary key.
				properties.setProperty("ycl_abc3");
				
				insert.execute(new BeanPropertySqlParameterSource(properties));
			}
			
		});
		
		System.out.println(bulk);
	}
	
	/**
	 * take attention, it just support simple type.
	 * 
	 * CREATE OR REPLACE TYPE myarray IS TABLE OF VARCHAR2 (100);
	 *
	 * CREATE OR REPLACE FUNCTION plm_get_weekends(iIMId NUMBER)
	 * RETURN myarray
	 * IS
     * t_weekends myarray :=myarray();
     * BEGIN
     * t_weekends.extend;
     * t_weekends(1) := SUNDAY;
     * t_weekends.extend;
     * t_weekends(2) := SATURDAY;
     * return myarray;
     * END;
     * 
	 * 
	 */
	public void testCallFunction(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate) context.getBean("jdbcTemplate");
		SimpleJdbcCall call = new SimpleJdbcCall(template);
		//call.withFunctionName("plm_get_weekends");
		call.withFunctionName("plm_get_setnames");
		Map<String, Object> args = new HashMap<String, Object>();
		//args.put("iIMId", 1000L);
		args.put("iReconId", 1810466L);
		String results = call.executeFunction(String.class, args);
		
		System.out.println(results);
	}
	
	/**
	 * 
CREATE OR REPLACE PROCEDURE ycl_gen_insert (tableName IN VARCHAR2,whereClause IN VARCHAR2)
AS
   v_columns VARCHAR2(1000);
   v_sel_columns VARCHAR2(1000);
   v_sql VARCHAR2(1000);
   v_index NUMBER;
   v_first BOOLEAN;
   v_cur_results  SYS_REFCURSOR;
   v_result VARCHAR2(1000);
BEGIN
   v_first := TRUE;


   FOR i IN (SELECT * FROM USER_TAB_COLUMNS WHERE table_name=tableName)
   LOOP
     IF (v_first) THEN
        IF (i.DATA_TYPE <> 'NUMBER') THEN
           v_columns := v_columns||i.COLUMN_NAME;
           v_sel_columns := v_sel_columns||'''||'||i.COLUMN_NAME||'||''';
           v_first :=FALSE;
        ELSE
           v_columns := v_columns||i.COLUMN_NAME;
           v_sel_columns := v_sel_columns||i.COLUMN_NAME;
           v_first :=FALSE;
        END IF;

     ELSE
        IF (i.DATA_TYPE <> 'NUMBER') THEN
           v_columns := v_columns||','||i.COLUMN_NAME;
           v_sel_columns := v_sel_columns||'||'',''''''||'||i.COLUMN_NAME||'||''''''''';
        ELSE
           v_columns := v_columns||','||i.COLUMN_NAME;
           v_sel_columns := v_sel_columns||'||'',''||'||i.COLUMN_NAME;
        END IF;
     END IF;
   END LOOP;
   DBMS_OUTPUT.PUT_LINE(v_sel_columns);

   v_sql := 'SELECT '||v_sel_columns||' FROM '||tableName||' WHERE '||whereClause;
   OPEN v_cur_results FOR v_sql;
    LOOP
      FETCH v_cur_results into v_result;
      EXIT WHEN v_cur_results%NOTFOUND;
      DBMS_OUTPUT.PUT_LINE('INSERT INTO '||tableName||'('||v_columns||') VALUES('||v_result||');');
    END LOOP;
   CLOSE v_cur_results;
END;
/
	 */
	public void testCallStoreProcedure(){
		ApplicationContext context = SpringUtils.getContext();
		JdbcTemplate template = (JdbcTemplate) context.getBean("jdbcTemplate"); 
		SimpleJdbcCall call = new SimpleJdbcCall(template); 
		call.withProcedureName("ycl_gen_insert");
//		call.withoutProcedureColumnMetaDataAccess();
//		call.useInParameterNames("tableName","whereClause");
//		call.declareParameters(new SqlParameter("tableName", Types.VARCHAR),new SqlParameter("whereClause", Types.VARCHAR));
		Map<String, Object> args = new HashMap<String, Object>();
		//args.put("iIMId", 1000L);
		args.put("tableName", "PLM_PROPERTIES");
		args.put("whereClause", "propertyid=9879");
		call.execute(args);
		
		System.out.println("no output");
	}
}
