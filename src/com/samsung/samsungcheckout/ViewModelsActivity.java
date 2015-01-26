package com.samsung.samsungcheckout;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ViewModelsActivity extends Activity {

	private List<Device> deviceList= new ArrayList<Device>();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the View layer
		setContentView(R.layout.listview);
		setTitle("Model Details");

		Device device = (Device) getIntent().getSerializableExtra("DeviceID");

		// Get Choices
		List<Choice> choiseList = device.choiceList;
	
		// Create a customized ArrayAdapter
		ChoiceArrayAdapter adapter = new ChoiceArrayAdapter(
				getApplicationContext(), R.layout.choiceadaptor, choiseList);
		
		// Get reference to ListView holder
		ListView lv = (ListView) this.findViewById(R.id.deviceLV);

		// Set the ListView adapter
		lv.setAdapter(adapter);		
			
	}
}
