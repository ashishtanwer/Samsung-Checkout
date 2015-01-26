package com.samsung.samsungcheckout;

import java.io.Serializable;

import android.util.Log;

public class Order implements Serializable{
	
	public String  device;
	public String quantity;
	public String model;
 
	Order(String  device, String  model, String quantity)
	{
        Log.d("Order","Order Constructor Called");
		this.device = device;
		this.model = model;
		this.quantity = quantity;		
	}	
	
	
	@Override
	public String toString() {
		return this.device+this.model+this.quantity;
	}
}
