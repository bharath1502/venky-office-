package com.chatak.pg.util;

import java.util.Enumeration;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Raj
 * 
 */
public class JacksonObjectMapperBuilder {
  
  private JacksonObjectMapperBuilder() {
 // Do nothing
  }
  
	public static ObjectMapper createObjectMapper(Properties prop)
			throws ClassNotFoundException {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.setSerializationInclusion(Include.NON_NULL);
		Enumeration<Object> keys = prop.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = (String) prop.get(key);
			Class<?> targetClass = Class.forName(key);
			Class<?> mixInClass = Class.forName(value);
			objMapper.addMixIn(targetClass, mixInClass);
		}
		return objMapper;
	}

}
