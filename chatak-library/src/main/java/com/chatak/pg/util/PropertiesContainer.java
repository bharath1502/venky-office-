package com.chatak.pg.util;

import java.util.Properties;

public class PropertiesContainer {

  private static PropertiesContainer container = new PropertiesContainer();

  private Properties propsExported = new Properties();

  private PropertiesContainer() {
    //To avoid Instantiation
  }

  public static synchronized PropertiesContainer getInstance() {
    return container;
  }

  public Properties getPropsExported() {
    return propsExported;
  }

  public void setPropsExported(Properties propsExported) {
    this.propsExported = propsExported;
  }

}
