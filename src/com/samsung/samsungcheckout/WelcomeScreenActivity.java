package com.samsung.samsungcheckout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.samsung.samsungcheckout.R;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Files;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WelcomeScreenActivity extends Activity {
	private static final String tag = "Welcome Screen";
	
	private static void copyFile(File source, File dest)
		        throws IOException {
		    InputStream input = null;
		    OutputStream output = null;
	    try {
	       input = new FileInputStream(source);
		        output = new FileOutputStream(dest);
		        byte[] buf = new byte[1024];
		        int bytesRead;
		        while ((bytesRead = input.read(buf)) > 0) {
		            output.write(buf, 0, bytesRead);
		        }
		    } finally {
		        input.close();
		        output.close();
		    }
		}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED) {
			// action cancelled
		}
		if (resultCode == RESULT_OK) {
			Uri uri = null;
			if (data != null) {
				Log.d(tag, "XML Opened Successfully");
				uri = data.getData();
				Log.d(tag, "Uri: " + uri.toString());
				InventoryXMLParser inventoryXMLParser = new InventoryXMLParser();
				InputStream newinputStream;
				try {
					newinputStream = new FileInputStream(uri.getPath());
					Log.d(tag, "Parsing the New Inventory XML");
					// Parse the inputstream
					inventoryXMLParser.parse(newinputStream);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Get Devices
				List<Device> newdeviceList = inventoryXMLParser.getList();

				Log.d(tag, "Reading the Inventory XML");
				InventoryXMLParser xMLParser1 = new InventoryXMLParser();
				InputStream inputStream;
				try {
					inputStream = new FileInputStream("/sdcard/inventory.xml");
					Log.d(tag, "Parsing the Inventory XML");
					// Parse the inputstream
					xMLParser1.parse(inputStream);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Get Devices
				List<Device> deviceList = xMLParser1.getList();

				for (Device nd : newdeviceList) {
					Boolean isnewd = true;
					for (Device d : deviceList) {
						// Existing Device is Added
						if (d.name.equalsIgnoreCase(nd.name) ) {
					        Log.d(tag,"Device Present in DeviceList"); 
							d=d.add(nd);
							isnewd = false;
							break;
						}
					}
					// New Device Is Added
					if (isnewd == true) {
				        Log.d(tag,"New Device Added in DeviceList"); 
						deviceList.add(nd);
					}
				}

				Log.d(tag, "Writing the DeviceList to XML with Added Devices");
				InventoryXMLWriter inventoryXMLWriter = new InventoryXMLWriter();
				inventoryXMLWriter.write(deviceList, "/sdcard/inventory.xml");
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomescreen);

		File f = new File("/sdcard/inventory.xml");
		if (!f.exists() || f.isDirectory()) {
			InventoryXMLParser inventoryXMLParser = new InventoryXMLParser();
			InputStream inputStream = getResources().openRawResource(
					R.raw.inventory);
			Log.d(tag, "Parsing the Resource Inventory XML");
			// Parse the inputstream
			inventoryXMLParser.parse(inputStream);

			// Get Devices
			List<Device> deviceList = inventoryXMLParser.getList();
			Log.d(tag, "Writing the DeviceList to XML");
			InventoryXMLWriter inventoryXMLWriter = new InventoryXMLWriter();
			inventoryXMLWriter.write(deviceList, "/sdcard/inventory.xml");
		}

		File f1 = new File("/sdcard/orders.xml");
		if (!f1.exists() || f1.isDirectory()) {
			OrdersXMLParser ordersXMLParser = new OrdersXMLParser();
			InputStream inputStream1 = getResources().openRawResource(R.raw.orders);
			Log.d(tag, "Parsing the Resource Orders XML");
			// Parse the inputstream
			ordersXMLParser.parse(inputStream1);

			// Get Devices
			List<Order> orderList = ordersXMLParser.getList();
			Log.d(tag, "Writing the DeviceList to XML");
			OrdersXMLWriter ordersXMLWriter = new OrdersXMLWriter();
			ordersXMLWriter.write(orderList, "/sdcard/orders.xml");
		}

		Log.d(tag, "Welcome Screen Activity Created");
		final Button viewInventoryButton = (Button) findViewById(R.id.viewInventory_Button);
		viewInventoryButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(tag, "Calling View Inventory Activity");
				Intent statsIntent = new Intent(WelcomeScreenActivity.this,
						ViewInventoryActivity.class);
				startActivity(statsIntent);

			}
		});
		
		final Button viewOrdersButton = (Button) findViewById(R.id.viewOrders_Button);
		viewOrdersButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(tag, "Calling View Inventory Activity");
				Intent statsIntent = new Intent(WelcomeScreenActivity.this,
						ViewOrdersActivity.class);
				startActivity(statsIntent);

			}
		});

		final Button addDeviceButton = (Button) findViewById(R.id.addDevice_Button);
		addDeviceButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(tag, "Calling Add Device Activity");
				Intent statsIntent = new Intent(WelcomeScreenActivity.this,
						AddDeviceActivity.class);
				startActivity(statsIntent);

			}
		});
		
		final Button addModelButton = (Button) findViewById(R.id.addModel_Button);
		addModelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(tag, "Calling Add Model Activity");
				Intent statsIntent = new Intent(WelcomeScreenActivity.this,
						AddModelActivity.class);
				startActivity(statsIntent);

			}
		});

		final Button addInventoryDBButton = (Button) findViewById(R.id.addInventoryDB_Button);
		addInventoryDBButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(tag, "Calling Add Inventory DB Activity");

				Intent intent = new Intent();
				intent.setType("text/xml");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Choose XML"), 1);
			}
		});
		
		final Button placeOrderButton = (Button) findViewById(R.id.placeOrder_Button);
		placeOrderButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.d(tag, "Calling Place Order Activity");
				Intent statsIntent = new Intent(WelcomeScreenActivity.this,
						PlaceOrderActivity.class);
				startActivity(statsIntent);

			}
		});
		
		final Button exportinventoryButton = (Button) findViewById(R.id.exportinventory_Button);
		exportinventoryButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			    Date date=new Date();
			    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

			    try {
			        date = df.parse(String.valueOf(date.getTime()));
			    } catch (ParseException e) {
			        throw new RuntimeException("Failed to parse date: ", e);
			    } catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d(tag, "Calling exportinventory");
				try {
					copyFile(new File("/sdcard/inventory.xml"), new File("/sdcard/inventory.xml"+date.getTime()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}
}
