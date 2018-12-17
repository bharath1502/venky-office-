package com.chatak.pg.server.config;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.xpath.CachedXPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.NodeIterator;

import com.chatak.pg.exception.ConfigException;
import com.chatak.pg.util.FileUtil;

/**
 * This class implements an abstract application configuration. Derived classes
 * just need to define the application specific configuration groups and
 * individual configuration keys. Presently a simple two tier XML config file
 * format is supported looking like the following: <config> <group 1> <key
 * 1>value</key 1> . . . <key n>value</key n> </group> . . . <group n > </group
 * n> </config>
 */
public abstract class ApplicationConfig {

  // various configuration initialization
  protected static String CONFIG_ROOT_ELEMENT = "config";

  protected static int INVALID_INT_VALUE = -1;

  // get class logger
  static Logger logger = Logger.getLogger(ApplicationConfig.class);;

  // DOM configuration document
  protected static Document mDocument = null;

  protected static CachedXPathAPI mCachedXPathAPI = null;

  /**
   * This method reads the XML configuration file into a DOM document. This DOM
   * document is used to update the corresponding configuration user interface.
   * 
   * @param path
   *          XML configuration file path
   */
  public static void readConfigFile() throws ConfigException {
    try {
      if(null == mDocument) {
        // create and configure DOM document builder factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);

        // get new document builder from factory
        DocumentBuilder db = dbf.newDocumentBuilder();

        // parse config file
        mDocument = db.parse(FileUtil.getConfigFileInputStream());

        mCachedXPathAPI = new CachedXPathAPI();
      }
    }
    catch(Exception e) {
      logger.error("Unable to load config file. ", e);
      throw new ConfigException("Unable to load config file. ", e);
    }
  }

  /**
   * This method writes out the DOM document to the file path indicated.
   * 
   * @param path
   *          XML configuration file path
   */
  public static boolean writeConfigFile(String path) {
    boolean successful = false;
    if(mDocument != null) {
      try {
        // use a transformer for output
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();

        // setup source and result of transform
        DOMSource source = new DOMSource(mDocument);
        StreamResult result = new StreamResult(new File(path));

        // use identity transform to write out DOM document
        transformer.transform(source, result);

        // everything ok
        successful = true;
      }
      catch(Exception e) {
        logger.error("ApplicationConfig.saveConfigFile " + "caught exception saving file " + path);
        e.printStackTrace();
        logger.error(e.getMessage(), e);
      }
    }
    else {
      logger.error("ApplicationConfig.saveConfigFile " + "mDocument not initialized ");
    }
    return successful;
  }

  /**
   * This method reads a string value from the DOM document corresponding to the
   * config group and key values.
   * 
   * @param group
   *          configuration group
   * @param key
   *          configuration key
   * @return config value as a String
   */
  public static NodeIterator getNodes(String group) {
    NodeIterator nl = null;
    if(mDocument != null) {
      try {
        // construct XPath query
        String xpathStr = "//" + CONFIG_ROOT_ELEMENT + "/" + group;
        nl = mCachedXPathAPI.selectNodeIterator(mDocument, xpathStr);
      }
      catch(TransformerException e) {
        logger.error("ApplicationConfig.getConfigValue  " + e.getMessage(), e);
      }
    }
    else {
      logger.error("ApplicationConfig.getConfigValue  " + " mDocument not initialized ");
    }
    return nl;
  }

  /**
   * This method get nodes based on attributes
   * 
   * @param group
   * @param attr
   * @param attrVal
   * @return NodeIterator
   */
  public static NodeIterator getNodesForAttribute(String group, String attr, String attrVal) {
    NodeIterator nodeIter = null;
    String xpathStr = createXPathStringForAttr(group, attr, attrVal);
    if(mDocument != null) {
      try {
        nodeIter = mCachedXPathAPI.selectNodeIterator(mDocument, xpathStr);
      }
      catch(TransformerException e) {
        logger.error("ApplicationConfig.getNodeForAttribute " + e.getMessage(), e);
      }
    }
    else {
      logger.error("ApplicationConfig.getNodeForAttribute is NULL for " + "group::" + group + "    attr::" + attr
                   + "   attrVal::" + attrVal + " config document not initialized ");
    }
    return nodeIter;
  }

  /**
   * This method get a node
   * 
   * @param group
   * @return Node
   */
  public static Node getNode(String group) {
    return getNodeForAttribute(group, null, null);
  }

  /**
   * This method get a node based on attribute
   * 
   * @param group
   * @param attr
   * @param attrVal
   * @return Node
   */
  public synchronized static Node getNodeForAttribute(String group, String attr, String attrVal) {
    Node node = null;
    String xpathStr = createXPathStringForAttr(group, attr, attrVal);
    if(mDocument != null) {
      try {
        if(mCachedXPathAPI == null)
          mCachedXPathAPI = new CachedXPathAPI();
        node = mCachedXPathAPI.selectSingleNode(mDocument, xpathStr);
      }
      catch(TransformerException e) {
        e.printStackTrace();
        logger.error("ApplicationConfig.getNodeForAttribute " + e.getMessage(), e);
      }
    }
    else {
      logger.error("ApplicationConfig::getNodeForAttribute() " + "group::" + group + "    attr::" + attr
                   + "   attrVal::" + attrVal + " config document not initialized ");

    }
    return node;
  }

  /**
   * This method create a node
   * 
   * @param parentNode
   * @param nodeName
   * @param attr
   * @param attrVal
   * @param text
   * @return Node
   */
  public static Node createNode(Node parentNode, String nodeName, String attr, String attrVal, String text) {
    Element elem = null;
    if(mDocument != null) {
      if(parentNode == null)
        parentNode = mDocument.getFirstChild();
      elem = mDocument.createElement(nodeName);
      parentNode.appendChild(elem);
      // Add attribute pair (attrName="attrValue")
      if(attr != null && attr.trim().length() > 0 && attrVal != null)
        elem.setAttribute(attr, attrVal);
      // Add text node
      if(text != null && text.trim().length() > 0) {
        Text txtNode = mDocument.createTextNode(text);
        elem.appendChild(txtNode);
      }
    }
    else {
      logger.error("ApplicationConfig.getNodeForAttribute " + "text::" + text + "    attr::" + attr + "   attrVal::"
                   + attrVal + " config document not initialized ");
    }
    return elem;
  }

  /**
   * This method create a node
   * 
   * @param group
   * @return Node
   */
  public static Node createNode(String group) {
    return createNode(null, group, null, null, null);
  }

  /**
   * This method get the node key value
   * 
   * @param node
   * @param key
   * @return String
   */
  public static String getString(Node node, String key) {
    String value = null;

    if(node != null) {
      try {
        // construct XPath query
        String xpathStr = key + "/text()";
        Node txtNode = mCachedXPathAPI.selectSingleNode(node, xpathStr);
        if(txtNode != null)
          value = txtNode.getNodeValue();
      }
      catch(TransformerException e) {
        logger.error("ApplicationConfig.getString(node, key) - " + e.getMessage(), e);
      }
    }
    else {
      logger.error("ApplicationConfig.getString(node, key)" + "key::" + key + "    node::" + node
                   + " - config document not initialized ");
    }
    return value;
  }

  /**
   * This method reads a int value from the DOM document corresponding to the
   * config group and key values.
   * 
   * @param group
   *          configuration group
   * @param key
   *          configuration key
   * @return config value as an int
   */
  public static int getInt(Node node, String key) {
    int value = INVALID_INT_VALUE;

    try {
      value = Integer.parseInt(getString(node, key));
    }
    catch(NumberFormatException e) {
      logger.error("ApplicationConfig.getInt(node, key), invalid value: " + e.getMessage(), e);
    }

    return value;
  }

  /**
   * This method set the node key value
   * 
   * @param parentNode
   * @param key
   * @param value
   */
  public static void setString(Node parentNode, String key, String value) {

    if(parentNode != null) {
      try {
        // construct XPath query
        String xpathStr = key + "/text()";
        Node txtNode = mCachedXPathAPI.selectSingleNode(parentNode, xpathStr);
        if(txtNode == null) {
          txtNode = mDocument.createTextNode(value);
          xpathStr = key;
          Node keyNode = mCachedXPathAPI.selectSingleNode(parentNode, xpathStr);
          if(keyNode == null) {
            keyNode = mDocument.createElement(key);
            parentNode.appendChild(keyNode);
          }
          keyNode.appendChild(txtNode);
        }
        txtNode.setNodeValue(value);
      }
      catch(TransformerException e) {
        logger.error("ApplicationConfig.setString(node, key, value) - " + e.getMessage(), e);
      }
    }
    else {
      logger.error("ApplicationConfig.getString(node, key)" + " - config document not initialized ");
    }
  }

  /**
   * This method set integer value for node key
   * 
   * @param node
   * @param key
   * @param value
   */
  public static void setInt(Node node, String key, int value) {
    setString(node, key, String.valueOf(value));
  }

  /**
   * This method create xpath string for attribute
   * 
   * @param group
   * @param attr
   * @param attrVal
   * @return String
   */
  private static String createXPathStringForAttr(String group, String attr, String attrVal) {
    String xpathStr = "//" + CONFIG_ROOT_ELEMENT + "/" + group;
    if(attr != null && !attr.trim().equals("") && !attr.equals("*")) {
      if(attrVal == null || attr.trim().equals(""))
        xpathStr += "[@" + attr + "]";
      else
        xpathStr += "[@" + attr + "=\"" + attrVal + "\"]";
    }
    return xpathStr;
  }
}