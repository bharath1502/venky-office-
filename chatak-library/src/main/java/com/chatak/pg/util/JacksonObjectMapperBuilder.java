package com.chatak.pg.util;

import java.util.Enumeration;
import java.util.Properties;

/*import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;*/

import org.codehaus.jackson.map.ObjectMapper;


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
		Enumeration<Object> keys = prop.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = (String) prop.get(key);
			Class<?> targetClass = Class.forName(key);
			Class<?> mixInClass = Class.forName(value);
			objMapper.getSerializationConfig().addMixInAnnotations(targetClass,
					mixInClass);
			objMapper.getDeserializationConfig().addMixInAnnotations(
					targetClass, mixInClass);
		}
		return objMapper;
	}

}
