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

public class OrdersArrayAdapter extends ArrayAdapter<Order> {
	private Context context;
	private static final String tag = "OrderArrayAdaptor";
	/** 	public String  device;
	public String quantity;
	public String model;
 
	Order(String  device, String  model, String quantity) */
	
	private TextView deviceType;
	private TextView deviceName;
	private TextView devicePrice;
	private TextView deviceOS;
	private TextView orderModel;
	private TextView orderQuantity;
	private List<Order> orders = new ArrayList<Order>();

	public OrdersArrayAdapter(Context context, int textViewResourceId,
			List<Order> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.orders = objects;
	}

	public int getCount() {
		return this.orders.size();
	}

	public Order getItem(int index) {
		return this.orders.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			// ROW INFLATION
			Log.d(tag, "Starting XML Row Inflation ... ");
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.ordersadaptor, parent, false);
			Log.d(tag, "Successfully completed XML Row Inflation!");
		}

		// Get item
		Order order = getItem(position);	
		deviceName = (TextView) row.findViewById(R.id.device_name);
		orderModel = (TextView) row.findViewById(R.id.order_model);
		orderQuantity = (TextView) row.findViewById(R.id.order_quantity);
		
		deviceName.setText(order.device);
		orderModel.setText(order.model);
		orderQuantity.setText(order.quantity);		

		return row;
	}

}
