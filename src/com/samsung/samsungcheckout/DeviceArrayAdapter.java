package com.samsung.samsungcheckout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeviceArrayAdapter extends ArrayAdapter<Device> {
	private Context context;
	private static final String tag = "DeviceArrayAdaptor";
	/** public String type;
	public String name;
	public int price;
	public String OS;
	public String resolution;
	public String camera;
	public Choice DeviceChoise;
	public Device(String type, String name, int price, String OS, String resolution,
	        String camera, Choice DeviceChoice[]) */
	
	private TextView deviceType;
	private TextView deviceName;
	private TextView devicePrice;
	private TextView deviceOS;
	private TextView deviceResolution;
	private TextView deviceCamera;
	private List<Device> devices = new ArrayList<Device>();

	public DeviceArrayAdapter(Context context, int textViewResourceId,
			List<Device> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.devices = objects;
	}

	public int getCount() {
		return this.devices.size();
	}

	public Device getItem(int index) {
		return this.devices.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			// ROW INFLATION
			Log.d(tag, "Starting XML Row Inflation ... ");
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.deviceadaptor, parent, false);
			Log.d(tag, "Successfully completed XML Row Inflation!");
		}

		// Get item
		Device device = getItem(position);
		deviceType = (TextView) row.findViewById(R.id.device_type);		
		deviceName = (TextView) row.findViewById(R.id.device_name);
		devicePrice = (TextView) row.findViewById(R.id.device_price);
		deviceOS = (TextView) row.findViewById(R.id.device_os);
		deviceResolution = (TextView) row.findViewById(R.id.device_resolution);
		deviceCamera = (TextView) row.findViewById(R.id.device_camera);
		
		deviceType.setText(device.type);
		deviceName.setText(device.name);
		devicePrice.setText(device.price);
		deviceOS.setText(device.OS);
		deviceResolution.setText(device.resolution);
		deviceCamera.setText(device.camera);		

		return row;
	}

}
