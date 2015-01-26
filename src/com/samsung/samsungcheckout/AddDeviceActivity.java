package com.samsung.samsungcheckout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
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
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AddDeviceActivity extends Activity {
	TextView deviceType;
	TextView deviceName;
	TextView devicePrice;
	TextView deviceOS;
	TextView deviceResolution;
	TextView deviceCamera;
	TextView choiceMemory;
	TextView choiceColor;
	TextView choiceQuantity;
	TextView choiceModel;
	final String tag="AddDeviceActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adddevice);
		
        Log.d(tag,"AddDeviceActivity Created");
        
		deviceType = (TextView) findViewById(R.id.device_type1);
		deviceName = (TextView) findViewById(R.id.device_name1);
		devicePrice = (TextView) findViewById(R.id.device_price1);
		deviceOS = (TextView) findViewById(R.id.device_os1);
		deviceResolution = (TextView) findViewById(R.id.device_resolution1);
		deviceCamera = (TextView) findViewById(R.id.device_camera1);
		choiceMemory = (TextView) findViewById(R.id.choice_memory);
		choiceColor = (TextView) findViewById(R.id.choice_color);
		choiceQuantity = (TextView) findViewById(R.id.choice_quantity);
		choiceModel = (TextView) findViewById(R.id.choice_model);
		
		final Button submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		        Log.d(tag,"Submit Button Clicked"); 
				String devicetype = (String) deviceType.getText().toString();
				String devicename = (String) deviceName.getText().toString();
				String deviceprice = (String) devicePrice.getText().toString();
				String deviceos = (String) deviceOS.getText().toString();
				String deviceresolution = (String) deviceResolution.getText().toString();
				String devicecamera = (String) deviceCamera.getText().toString();


				List<Choice> choiceList = new ArrayList<Choice>();

		        Log.d(tag,"New Device Created"); 
				Device newdevice = new Device(devicetype, devicename,
						deviceprice, deviceos, deviceresolution, devicecamera,
						choiceList);

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
				List<Device> deviceList = inventoryXMLParser.getList();

		        Log.d(tag,"Adding Device to the DeviceList"); 
				Boolean isnewd = true;
				for (Device d : deviceList) {
					// Existing Device is Added
			        Log.d(tag,"Device Present in DeviceList"); 
					if (d.name.equalsIgnoreCase(newdevice.name) ) {
						d.add(newdevice);
						isnewd = false;
						break;
					}
				}
				// New Device Is Added
				if (isnewd == true) {
			        Log.d(tag,"New Device Added in DeviceList"); 
					deviceList.add(newdevice);
				}
		        Log.d(tag, "Writing the DeviceList to XML");			
				InventoryXMLWriter inventoryXMLWriter=new InventoryXMLWriter();
				inventoryXMLWriter.write(deviceList,"/sdcard/inventory.xml");
				

				Intent statsIntent = new Intent(AddDeviceActivity.this,
						WelcomeScreenActivity.class);
				startActivity(statsIntent);

			}
		});

	}
}