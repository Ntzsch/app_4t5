package edu.gatech.cs2340.app_4t5.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.gatech.cs2340.app_4t5.R;
import edu.gatech.cs2340.app_4t5.models.User;
import edu.gatech.cs2340.app_4t5.models.UserRecords;

public class LoginActivity extends AppCompatActivity {
    private EditText username_text;
    private EditText password_text;
    private Button login_button;
    private TextView register_button;
    private TextView login_error_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TEMPORARY
        UserRecords.add_user(new User("user", "pass"));

        setContentView(R.layout.activity_login);
        username_text = findViewById(R.id.username_text);
        password_text = findViewById(R.id.password_text);
        login_button = findViewById(R.id.button);
        register_button = findViewById(R.id.register_button);
        login_error_view = findViewById(R.id.login_error_view);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_text.getText().toString();
                String password = password_text.getText().toString();
                password_text.setText("");
                User potential_match = UserRecords.get_user(username);
                if (potential_match != null && potential_match.is_correct_password(password)) {
                    // login
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                    startActivity(i);
                }
                else {
                    login_error_view.setVisibility(View.VISIBLE);
                    password_text.setText("");
                }
            }
        });


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });


    }
}
