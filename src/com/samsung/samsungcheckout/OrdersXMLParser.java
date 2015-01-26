package com.samsung.samsungcheckout;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;







import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

import android.util.Log;


	   
public class OrdersXMLParser {

	private static final String tag = "OrdersXMLParser";
	
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private final HashMap<String, String> map;
	private final List<Order> list;


	public OrdersXMLParser() {
		this.list = new ArrayList<Order>();
		this.map = new HashMap<String, String>();
	}

	private String getNodeValue(NamedNodeMap map, String key) {
		String nodeValue = null;
		Node node = map.getNamedItem(key);
		if (node != null) {
			nodeValue = node.getNodeValue();
		}
		return nodeValue;
	}

	public List<Order> getList() {
		return this.list;
	}

	public static String getCharacterDataFromElement(Element e) {
		if(e==null)
			return "";
		   Node child = e.getFirstChild();
		   if (child instanceof CharacterData) {
		     CharacterData cd = (CharacterData) child;
		       return cd.getData();
		     }
		   return "?";
	}
	
	public String getAbbreviation(String device) {
		return (String) this.map.get(device);
	}

	/** 	public String  device;
	public String quantity;
	public String model;
 
	Order(String  device, String  model, String quantity)	 */
	
	@SuppressWarnings("null")
	public void parse(InputStream inStream) {
		Log.d(tag, "Parsing the Order XML");
		try {
			// TODO: after we must do a cache of this XML!!!!
			this.factory = DocumentBuilderFactory.newInstance();
			this.builder = this.factory.newDocumentBuilder();
			this.builder.isValidating();

			Document doc = this.builder.parse(inStream, "UTF-8");

			doc.getDocumentElement().normalize();
              
			NodeList deviceList = doc.getElementsByTagName("order");
			final int length = deviceList.getLength();
			/*
			<name>Samsung GS5</name>
			<model>SM-B08</model>
			<quantity>10</quantity>
			 */
			for (int i = 0; i < length; i++) {
				final Element element = (Element)deviceList.item(i);

				NodeList name = element.getElementsByTagName("name");
			    Element line1 = (Element) name.item(0);
			    final String deviceName =getCharacterDataFromElement(line1);

				NodeList type = element.getElementsByTagName("model");
			    Element line2 = (Element) type.item(0);
			    final String deviceModel =getCharacterDataFromElement(line2);

				NodeList price = element.getElementsByTagName("quantity");
			    Element line3 = (Element) price.item(0);
			    final String deviceQuantity =getCharacterDataFromElement(line3);
	
			   // Construct Order object
				Order order = new Order(deviceName, deviceModel, deviceQuantity);
				
				// Add to list
				this.list.add(order);
				
				// Creat Map countrname-abbrev
				this.map.put(deviceName, deviceModel);
				//Log.d(tag, device.toString());*/
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
