package com.samsung.samsungcheckout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Device implements Serializable {

	    public String type;
		public String name;
		public String price;
		public String OS;
		public String resolution;
		public String camera;
		public List<Choice> choiceList;

		public Device() {
			// TODO Auto-generated constructor stub
		}

		public Device(String type, String name, String price, String OS, String resolution,
		        String camera, List<Choice> choiceList) {
	        Log.d("Device","Device Constructor Called");
			this.type = type;
			this.name = name;
			this.price = price;
			this.OS = OS;
			this.resolution = resolution;
			this.camera = camera;
			
			//Deep Copy
			this.choiceList = new ArrayList<Choice>(choiceList);

		}
		
		Device add(Device d)
		{
	        Log.d("Device","Device Add Called");
			for(Choice newc:d.choiceList)
			{
				Boolean isnewc=true;
				for(Choice c:this.choiceList)
				{
					// Existing Model is Found. Quantity updated
					if(c.model.equalsIgnoreCase(newc.model))
					{
				        Log.d("Device","Calling Choice Added");
						c=c.add(newc);
						isnewc=false;
					}					
				}
				// New Model Is Added
				if(isnewc==true)
				{
			        Log.d("Device","New Device Added");
					this.choiceList.add(newc); 
				}			
			}	
		return this;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public Device subtract(Order order) {
	        Log.d("Device","Device Subtract Called");

			Boolean isnewc=true;
			for(Choice c:this.choiceList)
			{
				// Existing Model is Found. Quantity updated
				if(c.model.equalsIgnoreCase(order.model))
				{
			        Log.d("Device","Calling Choice Subtract");
					c.subtract(order);
					isnewc=false;
				}					
			}
			// New Model Is Added
			if(isnewc==true)
			{
		        Log.d("Device","Requested Model is not Available"); 
			}			

		return this;
			
		}
}

