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


	   
public class InventoryXMLParser {

	private static final String tag = "InventoryXMLParser";
	
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private final HashMap<String, String> map;
	private final List<Device> list;
	private final List<Choice> clist;


	public InventoryXMLParser() {
		this.list = new ArrayList<Device>();
		this.clist = new ArrayList<Choice>();
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

	public List<Device> getList() {
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

	/** public String type;
		public String name;
		public int price;
		public String OS;
		public String resolution;
		public String camera;
		public Choice DeviceChoise;
		public Device(String type, String name, int price, String OS, String resolution,
		        String camera, Choice DeviceChoise[]) 	 */
	
	@SuppressWarnings("null")
	public void parse(InputStream inStream) {
		Log.d(tag, "Parsing the Inventory XML");
		try {
			// TODO: after we must do a cache of this XML!!!!
			this.factory = DocumentBuilderFactory.newInstance();
			this.builder = this.factory.newDocumentBuilder();
			this.builder.isValidating();

			Document doc = this.builder.parse(inStream, "UTF-8");

			doc.getDocumentElement().normalize();
              
			NodeList deviceList = doc.getElementsByTagName("device");
			final int length = deviceList.getLength();
			/*
			<type>Smartphone</type>
			<name>Samsung GS5</name>
			<price>$500</price>
			<OS>Android</OS>
			<resolution>1920 * 1280</resolution>
			<camera>16MP</camera>
			 */
			for (int i = 0; i < length; i++) {
				final Element element = (Element)deviceList.item(i);

				NodeList name = element.getElementsByTagName("name");
			    Element line1 = (Element) name.item(0);
			    final String deviceName =getCharacterDataFromElement(line1);

				NodeList type = element.getElementsByTagName("type");
			    Element line2 = (Element) type.item(0);
			    final String deviceType =getCharacterDataFromElement(line2);

				NodeList price = element.getElementsByTagName("price");
			    Element line3 = (Element) price.item(0);
			    final String devicePrice =getCharacterDataFromElement(line3);
	
				NodeList os = element.getElementsByTagName("OS");
			    Element line4 = (Element) os.item(0);
			    final String deviceOS =getCharacterDataFromElement(line4);
			    
				NodeList resolution = element.getElementsByTagName("resolution");
			    Element line5 = (Element) resolution.item(0);
			    final String deviceResolution =getCharacterDataFromElement(line5);	
			    
				NodeList camera = element.getElementsByTagName("camera");
			    Element line6 = (Element) camera.item(0);
			    final String deviceCamera =getCharacterDataFromElement(line6);	
			    
			    NodeList nodeList = element.getElementsByTagName("choice");
			    
			    for(int i1=0;i1<nodeList.getLength();i1++)
			    {
					/*<memory>8 GB</memory>
					<color>black</color>
					<quantity>10</quantity>
					<model>SM-B08</model>*/
					final Element element1 = (Element)nodeList.item(i1);

					NodeList memory = element1.getElementsByTagName("memory");
				    Element line11 = (Element) memory.item(0);
				    final String choiceMemory =getCharacterDataFromElement(line11);

					NodeList color = element1.getElementsByTagName("color");
				    Element line12 = (Element) color.item(0);
				    final String choiceColor =getCharacterDataFromElement(line12);

					NodeList quantity = element1.getElementsByTagName("quantity");
				    Element line13 = (Element) quantity.item(0);
				    final String choiceQuantity =getCharacterDataFromElement(line13);
		
					NodeList model = element1.getElementsByTagName("model");
				    Element line14 = (Element) model.item(0);
				    final String choiceModel =getCharacterDataFromElement(line14);
				    

					Choice choice = new Choice(choiceMemory, choiceColor, choiceQuantity, choiceModel);
					Log.d(tag, "New Choice Found for Device ");
					
					// Add to list
					clist.add(choice);
			    }		

				// Construct Device object
				Device device = new Device(deviceType, deviceName, devicePrice, deviceOS, deviceResolution,
						deviceCamera,clist);
				Log.d(tag, "New Device Found");
				clist.clear();
				
				// Add to list
				this.list.add(device);
				
				// Creat Map countrname-abbrev
				this.map.put(deviceName, deviceType);
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
