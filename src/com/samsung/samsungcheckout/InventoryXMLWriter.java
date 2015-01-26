package com.samsung.samsungcheckout;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import android.util.Log;
 
public class InventoryXMLWriter {
	
	private static final String tag = "InventoryXMLWriter";
	
	public void write(List<Device> dList,String filename) {
    try {
	    Log.d(tag, "Starting XML Writer... ");

		
		/*<inventory>
	    <device> 
		<type>Smartphone</type>
		<name>Samsung GS5</name>
		<price>$500</price>
		<OS>Android</OS>
		<resolution>1920 * 1280</resolution>
		<camera>16MP</camera>
		<choice>
			<memory>8 GB</memory>
			<color>black</color>
			<quantity>10</quantity>
			<model>SM-B08</model>
		</choice>*/
		
		// root elements
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("inventory");
		doc.appendChild(rootElement);   
		
		for(Device d:dList)
        {
			// device elements
			Element device = doc.createElement("device");
			rootElement.appendChild(device);
	
			// type elements
			Element type = doc.createElement("type");
			type.appendChild(doc.createTextNode(d.type));
			device.appendChild(type);
	 
			// name elements
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(d.name));
			device.appendChild(name);
	 
			// price elements
			Element price = doc.createElement("price");
			price.appendChild(doc.createTextNode(d.price));
			device.appendChild(price);
	 
			// salary elements
			Element OS = doc.createElement("OS");
			OS.appendChild(doc.createTextNode(d.OS));
			device.appendChild(OS);
			
			// resolution elements
			Element resolution = doc.createElement("resolution");
			resolution.appendChild(doc.createTextNode(d.resolution));
			device.appendChild(resolution);
			
			// camera elements
			Element camera = doc.createElement("camera");
			camera.appendChild(doc.createTextNode(d.camera));
			device.appendChild(camera);
		
			for(Choice c:d.choiceList)
			{
				// choice elements
				Element choice = doc.createElement("choice");
				device.appendChild(choice);
				// memory elements
				Element memory = doc.createElement("memory");
				memory.appendChild(doc.createTextNode(c.memory));
				choice.appendChild(memory);
				
				// color elements
				Element color = doc.createElement("color");
				color.appendChild(doc.createTextNode(c.color));
				choice.appendChild(color);
				
				// camera elements
				Element quantity = doc.createElement("quantity");
				quantity.appendChild(doc.createTextNode(c.quantity));
				choice.appendChild(quantity);
				
				// camera elements
				Element model = doc.createElement("model");
				model.appendChild(doc.createTextNode(c.model));
				choice.appendChild(model);
			}
        }
	    Log.d(tag, "Reading the DeviceList Done ");
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
	    Log.d(tag, "Creating the DOM Object ");
		DOMSource source = new DOMSource(doc);
	     
	    Log.d(tag, "Writing the DOM Object to File Stream");
		StreamResult result = new StreamResult(new File(filename));
		transformer.transform(source, result);

		/*
		StringWriter xmlAsWriter = new StringWriter();
		StreamResult result = new StreamResult(xmlAsWriter);
		transformer.transform(source, result);

		try {
			 this.inputStream = new ByteArrayInputStream(xmlAsWriter.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
}