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

public class ChoiceArrayAdapter extends ArrayAdapter<Choice> {
	private Context context;
	private static final String tag = "ChoiceArrayAdaptor";
	/** 	
	public String  memory;
	public String  color;
	public String quantity;
	public String model;
 
	Choice(String  memory, String  color, String quantity, String model)*/
	
	private TextView choiceMemory;
	private TextView choiceColor;
	private TextView choiceQuantity;
	private TextView choiceModel;
	private List<Choice> choices = new ArrayList<Choice>();

	public ChoiceArrayAdapter(Context context, int textViewResourceId,
			List<Choice> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.choices = objects;
	}

	public int getCount() {
		return this.choices.size();
	}

	public Choice getItem(int index) {
		return this.choices.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			// ROW INFLATION
			Log.d(tag, "Starting XML Row Inflation ... ");
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.choiceadaptor, parent, false);
			Log.d(tag, "Successfully completed XML Row Inflation!");
		}

		// Get item
		Choice choice = getItem(position);
		choiceMemory = (TextView) row.findViewById(R.id.choice_memory);		
		choiceColor = (TextView) row.findViewById(R.id.choice_color);
		choiceQuantity = (TextView) row.findViewById(R.id.choice_quantity);
		choiceModel = (TextView) row.findViewById(R.id.choice_model);
		
		choiceMemory.setText(choice.memory);
		choiceColor.setText(choice.color);
		choiceQuantity.setText(choice.quantity);
		choiceModel.setText(choice.model);
	

		return row;
	}

}
