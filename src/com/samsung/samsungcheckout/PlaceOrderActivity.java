package com.samsung.samsungcheckout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;




//import com.google.android.gms.location.LocationClient;
//import com.google.android.gms.location.LocationRequest;
import com.samsung.samsungcheckout.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender.SendIntentException;
import android.database.Cursor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceOrderActivity extends Activity {

	private final String tag = "PlaceOrderActivity";
	String selecteddevicename = null;
	String selectedchoicename = null;
	String selectedQuantityNumber = null;
	ArrayList<String> deviceNamesList = new ArrayList<String>();
	ArrayList<String> choiceNamesList = new ArrayList<String>();
	ArrayList<String> quantityNumberList = new ArrayList<String>();
	Device selectedDevice = null;
	String selectedQuantity = null;
	Spinner deviceName  ;
	Spinner choiceQuantity  ;
	Spinner choiceModel ;
	Button  submitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Restore any saved state
		super.onCreate(savedInstanceState);

		// Set content view
		setContentView(R.layout.placeorder);

		deviceName = (Spinner) findViewById(R.id.device_name);
		choiceQuantity = (Spinner) findViewById(R.id.choice_quantity);
		choiceModel = (Spinner) findViewById(R.id.choice_model);
		submitButton = (Button) findViewById(R.id.submitButton);

		// Create Parser for raw/devices.xml
		InventoryXMLParser inventoryXMLParser = new InventoryXMLParser();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("/sdcard/inventory.xml");
			Log.d(tag, "Parsing the Inventory XML");
			// Parse the inputstream
			inventoryXMLParser.parse(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get Devices
		Log.d(tag, "Created DeviceList from XML");
		final List<Device> deviceList = inventoryXMLParser.getList();
		
		
		
		
		OrdersXMLParser ordersXMLParser = new OrdersXMLParser();
		InputStream inputStream1;
		try {
			inputStream1 = new FileInputStream("/sdcard/orders.xml");
			Log.d(tag, "Parsing the Orders XML");
			// Parse the inputstream
			ordersXMLParser.parse(inputStream1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get Devices
		Log.d(tag, "Created OrderList from XML");
		final List<Order> orderList = ordersXMLParser.getList();
		
		
		
		
		
		

		List<Choice> choiceList = new ArrayList<Choice>();

		Log.d(tag, "Created DeviceNameList for Spinner Input");
		for (int i = 0; i < deviceList.size(); i++) {
			deviceNamesList.add(new String(deviceList.get(i).name));
		}

		Log.d(tag, "Creating Hastset for the Adaptor");
		String[] uniquedeviceNames = new HashSet<String>(deviceNamesList).toArray(new String[0]);

		Log.d(tag, "Setting Adaptor for deviceName Spinner");
		deviceName.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, uniquedeviceNames));

		deviceName.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView adapter, View v, int i,long lng) 
			{
				Log.d(tag, "Get selecteddevicename from Spinner");
				selecteddevicename = adapter.getItemAtPosition(i).toString();
				// or this can be also right: selecteditem = level[i];

				for (Device d : deviceList) {
					if (d.name.equalsIgnoreCase(selecteddevicename)) {
						selectedDevice = d;
						break;
					}
				}

				Log.d(tag, "Created ChoiceNameList for Spinner Input");
				choiceNamesList.clear();
				for (int i1 = 0; i1 < selectedDevice.choiceList.size(); i1++) {
					choiceNamesList.add(new String(selectedDevice.choiceList.get(i1).model));
				}

				String[] uniquechoiceNames = new HashSet<String>(choiceNamesList).toArray(new String[0]);
				// String[] wifiStringunique =uniquewifiString;
				Log.d(tag, "Setting Adaptor for choiceModel Spinner");
				choiceModel.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,uniquechoiceNames));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		choiceModel.setOnItemSelectedListener(new OnItemSelectedListener() {
			Choice selectedModel = null;


			@Override
			public void onItemSelected(AdapterView adapter,	View v, int i, long lng) {
				Log.d(tag,"Get selectedchoicename from Spinner");
				selectedchoicename = adapter.getItemAtPosition(i).toString();
				// or this can be also right: selecteditem =// level[i];
										
				for (Choice c : selectedDevice.choiceList) {
					if (c.model.equalsIgnoreCase(selectedchoicename)) {
						selectedModel = c;
					}
				}
				Log.d(tag, "Created QuantityNumberList for Spinner Input");
		        Integer newqualtity=0;
		        try{
		        	newqualtity=Integer.parseInt(selectedModel.quantity);
		        }
		        catch(Exception e)
		        {
		        	newqualtity=0;
		        }
				for (int i1 = 1; i1 <= newqualtity; i1++) {
					quantityNumberList.add(new String(String.valueOf(i1)));
				}
				Collections.sort(quantityNumberList);
				//String[] uniqueQuantityNumbers = new HashSet<String>(quantityNumberList).toArray(new String[0]);
				Log.d(tag, "Setting Adaptor for choiceModel Spinner");
				choiceQuantity.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,	quantityNumberList));	
			}
			

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});		
		
		submitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(tag, "Submit Button Clicked");
				String devicename = (String) deviceName.getSelectedItem()
						.toString();
				String choicequantity = (String) choiceQuantity
						.getSelectedItem().toString();
				String choicemodel = (String) choiceModel.getSelectedItem()
						.toString();

				Order order = new Order(devicename, choicemodel, choicequantity);

				Log.d(tag, "Subtracting Order from DeviceList");
				Boolean isnewd = true;
				for (Device d : deviceList) {
					// Existing Device is Added
					Log.d(tag, "Device Present in DeviceList");
					if (d.name.equalsIgnoreCase(order.device)) {
						d = d.subtract(order);
						isnewd = false;
						break;
					}
				}
				// New Device Is Added
				if (isnewd == true) {
					Log.d(tag, "Ordered Device Not present in DeviceList");
				}
				Log.d(tag, "Writing the DeviceList to XML");
				InventoryXMLWriter inventoryXMLWriter = new InventoryXMLWriter();
				inventoryXMLWriter.write(deviceList,"/sdcard/inventory.xml");
				
				Log.d(tag, "Adding Order from OrderList");
				orderList.add(order);
				Log.d(tag, "Writing the OrderList to XML");
				OrdersXMLWriter xMLWriter1 = new OrdersXMLWriter();
				xMLWriter1.write(orderList,"/sdcard/orders.xml");

				Intent statsIntent = new Intent(PlaceOrderActivity.this,
						WelcomeScreenActivity.class);
				startActivity(statsIntent);

			}
		});

	}

}
