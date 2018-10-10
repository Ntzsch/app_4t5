package edu.gatech.cs2340.app_4t5.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import edu.gatech.cs2340.app_4t5.R;
import edu.gatech.cs2340.app_4t5.models.User;
import edu.gatech.cs2340.app_4t5.models.UserRecords;

public class MainActivity extends AppCompatActivity{
    //private Button logout_button;
    private DrawerLayout mDrawerLayout;
    /**
     * hard coded examples below
     */
    private String[] name = {"Goodwill", "Salvation Army"};
    private String[] addresses = {"123 Goodwill st.", "123 random street"};
    private String[] typeOfAddresses = {"idk", "kdi"};
    private String[] phoneNumber = {"404-905-1888", "405-437-8868"};
    ListView listView;
    private TextView test;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_listview);

        CustomLocationAdapter location = new CustomLocationAdapter(this, addresses, typeOfAddresses, phoneNumber, name);

        listView = (ListView) findViewById(R.id.listviewLocation);
        listView.setAdapter(location);
        test = (TextView) findViewById(R.id.testTouch);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        //listens for a click on a particular location
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //detects tap on a listview item
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                    test.setText(name[position]); //display location name when clicked
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

//        logout_button = findViewById(R.id.logout_button);
//
//        logout_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                finish();
//                startActivity(i);
//            }
//        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
