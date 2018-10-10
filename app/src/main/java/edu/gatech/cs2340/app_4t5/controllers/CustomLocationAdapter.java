package edu.gatech.cs2340.app_4t5.controllers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.gatech.cs2340.app_4t5.R;

public class CustomLocationAdapter extends ArrayAdapter {
    private final Activity context; //not exactly sure what context is
    /**
     * all the attributes below are hardcoded examples.
     * having a location class would change all of this
     */
    private final String[] name;
    private final String[] addresses;
    private final String[] typeOfAddress;
    private final String[] phoneNumber;

    //constructor for CustomLocationAdapter
    public CustomLocationAdapter(Activity context, String[] addresses, String[] typeOfAddress, String[] phoneNumber, String[] name){

        super(context, R.layout.listview_row , addresses);
            this.context = context;
            this.addresses = addresses;
            this.typeOfAddress = typeOfAddress;
            this.phoneNumber = phoneNumber;
            this.name = name;
    }
    //populates each listview_row with information from the attributes
    public View getView(int position, View view, ViewGroup parent) { //
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView locationName = (TextView) rowView.findViewById(R.id.locationName);
        TextView locationAddress = (TextView) rowView.findViewById(R.id.locationAddress);
        TextView locationType = (TextView) rowView.findViewById(R.id.locationType);
        TextView phoneNum = (TextView) rowView.findViewById(R.id.phoneNumber);

        //this code sets the values of the objects to values from the arrays
        locationName.setText(name[position]);
        locationAddress.setText(addresses[position]);
        locationType.setText(typeOfAddress[position]);
        phoneNum.setText(phoneNumber[position]);

        return rowView;

    };
}
