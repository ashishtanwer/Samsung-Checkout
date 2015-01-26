package com.samsung.samsungcheckout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.samsung.samsungcheckout.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AddModelActivity extends Activity {
	Spinner deviceName;
	TextView choiceMemory;
	TextView choiceColor;
	TextView choiceQuantity;
	TextView choiceModel;
	final String tag="AddDeviceActivity";
	ArrayList<String> deviceNamesList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addmodel);
		
        Log.d(tag,"AddModelActivity Created");
        
		deviceName = (Spinner) findViewById(R.id.device_name);
		choiceMemory = (TextView) findViewById(R.id.choice_memory);
		choiceColor = (TextView) findViewById(R.id.choice_color);
		choiceQuantity = (TextView) findViewById(R.id.choice_quantity);
		choiceModel = (TextView) findViewById(R.id.choice_model);
		
		// Create Parser for raw/devices.xml
		InventoryXMLParser inventoryXMLParser = new InventoryXMLParser();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("/sdcard/inventory.xml");
	        Log.d(tag, "Parsing the Inventory XML");		
			// Parse the inputstream
			inventoryXMLParser.parse(inputStream);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get Devices
        Log.d(tag,"Created DeviceList from XML"); 
		final List<Device> deviceList = inventoryXMLParser.getList();
		
		Log.d(tag, "Created DeviceNameList for Spinner Input");
		for (int i = 0; i < deviceList.size(); i++) {
			deviceNamesList.add(new String(deviceList.get(i).name));
		}
		
		Log.d(tag, "Creating Hastset for the Adaptor");
		String[] uniquedeviceNames = new HashSet<String>(deviceNamesList).toArray(new String[0]);

		Log.d(tag, "Setting Adaptor for deviceName Spinner");
		deviceName.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, uniquedeviceNames));

		
		
		final Button submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		        Log.d(tag,"Submit Button Clicked"); 
				String devicename = (String) deviceName.getSelectedItem().toString();
				String choicememory = (String) choiceMemory.getText().toString();
				String choicecolor = (String) choiceColor.getText().toString();
				String choicequantity = (String) choiceQuantity.getText().toString();
				String choicemodel = (String) choiceModel.getText().toString();

		        Log.d(tag,"New Choice Created"); 
				Choice newchoice = new Choice(choicememory, choicecolor,
						choicequantity, choicemodel);			
				
				Boolean isnewd = true;
				for (Device d : deviceList) {
					// Existing Device is Added
			        Log.d(tag,"Device Present in DeviceList"); 
					if (d.name.equalsIgnoreCase(devicename) ) {
						Boolean isnewc = true;
						for(Choice c:d.choiceList)
						{
							if (c.model.equalsIgnoreCase(choicemodel) ) 
							{
								c=c.add(newchoice);
								isnewc = false;
								break;	
							}			
						}
						// New Device Is Added
						if (isnewc == true) {
							d.choiceList.add(newchoice);
					        Log.d(tag,"New Choice Added In Device"); 
						}
						
						isnewd = false;
						break;
					}
				}
				// New Device Is Added
				if (isnewd == true) {
			        Log.d(tag,"Device Not Found In DeviceList"); 
				}
		        Log.d(tag, "Writing the DeviceList to XML");			
				InventoryXMLWriter inventoryXMLWriter=new InventoryXMLWriter();
				inventoryXMLWriter.write(deviceList,"/sdcard/inventory.xml");
				

				Intent statsIntent = new Intent(AddModelActivity.this,
						WelcomeScreenActivity.class);
				startActivity(statsIntent);

			}
		});

	}
}