package com.samsung.samsungcheckout;

import java.io.Serializable;

import android.util.Log;

public class Choice implements Serializable{
	
	public String  memory;
	public String  color;
	public String quantity;
	public String model;
 
	Choice(String  memory, String  color, String quantity, String model)
	{
        Log.d("Choice","Choice Constructor Called");
		this.memory = memory;
		this.color = color;
		this.quantity = quantity;
		this.model = model;
		
	}	
	
	Choice add(Choice c)
	{
        Log.d("Choice","Choice Add Called");
        Integer newqualtity=0;
        try{
        	newqualtity=Integer.parseInt(c.quantity);
        }
        catch(Exception e)
        {
        	newqualtity=0;
        }
		this.quantity=String.valueOf(Integer.parseInt(this.quantity) +newqualtity);
		return this;
	}
	
	@Override
	public String toString() {
		return this.model;
	}

	public Choice subtract(Order order) {
        Log.d("Choice","Choice Subtract Called");
        Integer newqualtity=0;
        try{
        	newqualtity=Integer.parseInt(order.quantity);
        }
        catch(Exception e)
        {
        	newqualtity=0;
        }
		this.quantity=String.valueOf(Integer.parseInt(this.quantity) -newqualtity);
		return this;
		
	}
}
