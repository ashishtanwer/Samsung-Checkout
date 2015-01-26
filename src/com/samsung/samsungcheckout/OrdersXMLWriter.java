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
 
public class OrdersXMLWriter {
	
	private static final String tag = "InventoryXMLWriter";
	
	public void write(List<Order> oList,String filename) {
    try {
	    Log.d(tag, "Starting XML Writer... ");

		
		/*<order>
		<name>Samsung GS5</name>
		<model>SM-B08</model>
		<quantity>10</quantity>*/
		
		// root elements
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("purchase");
		doc.appendChild(rootElement);   
		
		for(Order d:oList)
        {
			// device elements
			Element device = doc.createElement("order");
			rootElement.appendChild(device);
	
			// name elements
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(d.device));
			device.appendChild(name);
	 
			// price elements
			Element price = doc.createElement("model");
			price.appendChild(doc.createTextNode(d.model));
			device.appendChild(price);
	 
			// salary elements
			Element OS = doc.createElement("quantity");
			OS.appendChild(doc.createTextNode(d.quantity));
			device.appendChild(OS);
 
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

 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
}