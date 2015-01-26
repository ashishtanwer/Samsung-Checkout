package com.samsung.samsungcheckout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ViewInventoryActivity extends Activity {
	final String tag="View Inventory Activity";

	private List<Device> deviceList= new ArrayList<Device>();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the View layer
		setContentView(R.layout.listview);
		setTitle("Inventory Details");
        Log.d(tag, "In View Inventory Activity");
		// Create Parser for raw/devices.xml
        Log.d(tag, "Reading the Inventory XML");
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
		List<Device> deviceList = inventoryXMLParser.getList();
		
		
		// Create a customized ArrayAdapter
        Log.d(tag, "Creating Devie Adapor");	
		DeviceArrayAdapter adapter = new DeviceArrayAdapter(
				getApplicationContext(), R.layout.deviceadaptor, deviceList);
		
		// Get reference to ListView holder
		ListView lv = (ListView) this.findViewById(R.id.deviceLV);
		// Set the ListView adapter
        Log.d(tag, "Setting the Devie Adapor to List View");	
		lv.setAdapter(adapter);	
		lv.setOnItemClickListener(new  OnItemClickListener(){
            
			public void onItemClick(AdapterView<?>adapter,View v, int position, long l){

			Device item = (Device) adapter.getItemAtPosition(position);
			//Toast.makeText(ViewInventoryActivity.this, "hi"+position, Toast.LENGTH_SHORT).show();
	        Log.d(tag, "Device Clicked. Calling ViewModelsActivity");
			Intent intent1 = new Intent(ViewInventoryActivity.this,ViewModelsActivity.class).putExtra("DeviceID",item);
			//based on item add info to intent
            startActivity(intent1);
			}

		});
	
			
	}
}
