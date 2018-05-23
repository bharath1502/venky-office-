package com.chatak.prepaid.velocity.impl;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.MethodInvocationException;
import org.springframework.stereotype.Service;

import com.chatak.prepaid.velocity.IVelocityTemplateCreator;

/**
 * This class is used to Implemented velocityXML (convert object to xml).
 * 
 * @author Girmiti Software
 * @date Jul 25, 2014 6:35:52 PM
 * @version 1.0
 */
@Service
public class VelocityTemplateCreatorImpl implements IVelocityTemplateCreator {

	private static VelocityEngine velocityEngine = null;

  public String createEmailTemplate(Map<String, String> requestMap, String templatePath) {
    VelocityContext context = new VelocityContext();
    Set<String> requestMapSet = requestMap.keySet();
    Iterator<String> requestMapSetIterator = requestMapSet.iterator();

    while (requestMapSetIterator.hasNext()) {
      String key = requestMapSetIterator.next();
      context.put(key, requestMap.get(key));
    }

    Template template = getVelocityEngineInstance().getTemplate(templatePath, "UTF-8");
    StringWriter writer = new StringWriter();
    template.merge(context, writer);
    return writer.toString();
  }

	private static VelocityEngine getVelocityEngineInstance() {
		if (velocityEngine == null) {
			velocityEngine = new VelocityEngine();
			velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			velocityEngine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
			velocityEngine.init();
		}
		
		return velocityEngine;
	}
}