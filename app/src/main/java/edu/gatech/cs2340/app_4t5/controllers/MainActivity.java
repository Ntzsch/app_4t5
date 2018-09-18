package edu.gatech.cs2340.app_4t5.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.gatech.cs2340.app_4t5.R;
import edu.gatech.cs2340.app_4t5.models.User;
import edu.gatech.cs2340.app_4t5.models.UserRecords;

public class MainActivity extends AppCompatActivity{
    private Button logout_button;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout_button = findViewById(R.id.logout_button);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                finish();
                startActivity(i);
            }
        });

    }
}
