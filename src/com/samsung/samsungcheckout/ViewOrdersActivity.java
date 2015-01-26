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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ViewOrdersActivity extends  Activity  {
	final String tag="View Order Activity";

	private List<Order> ordersList= new ArrayList<Order>();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the View layer
		setContentView(R.layout.listview);
		setTitle("Orders Details");
        Log.d(tag, "In View Orders Activity");
		// Create Parser for raw/orderss.xml
        Log.d(tag, "Reading the Orders XML");
        OrdersXMLParser Orders = new OrdersXMLParser();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("/sdcard/orders.xml");
	        Log.d(tag, "Parsing the Orders XML");		
			// Parse the inputstream
	        Orders.parse(inputStream);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Get Order
		List<Order> ordersList = Orders.getList();
		
		
		// Create a customized ArrayAdapter
        Log.d(tag, "Creating Devie Adapor");	
		OrdersArrayAdapter adapter = new OrdersArrayAdapter(
				getApplicationContext(), R.layout.ordersadaptor, ordersList);
		
		// Get reference to ListView holder
		ListView lv = (ListView) this.findViewById(R.id.deviceLV);
		// Set the ListView adapter
        Log.d(tag, "Setting the Order Adapor to List View");	
		lv.setAdapter(adapter);	
		
	}
}