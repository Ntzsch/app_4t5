package edu.gatech.cs2340.app_4t5.controllers;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.gatech.cs2340.app_4t5.R;
import edu.gatech.cs2340.app_4t5.models.User;
import edu.gatech.cs2340.app_4t5.models.UserRecords;

public class RegisterActivity extends AppCompatActivity {
    private EditText registerName;
    private EditText registerPass;
    private EditText registerPassAgain;
    private Button registerButton;
    private TextView passwordMatchError;
    private TextView usernameError;
    private TextView success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.editText3);
        registerPass = findViewById(R.id.editText);
        registerPassAgain = findViewById(R.id.editText2);
        registerButton = findViewById(R.id.button2);
        passwordMatchError = findViewById(R.id.error2);
        usernameError = findViewById(R.id.userError);
        success = findViewById(R.id.success);

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //text entered in username textbox
                String usernameR = registerName.getText().toString();
                //text entered in password textbox
                String passwordR1 = registerPass.getText().toString();
                //text entered in passwordagain textbox
                String passwordR2 = registerPassAgain.getText().toString();

                registerPass.setText("");
                registerPassAgain.setText("");

                if (UserRecords.containsUsername(usernameR)) {
                    usernameError.setVisibility(View.VISIBLE);
                    registerName.setText("");
                }
                if (!passwordR1.equals(passwordR2)) {
                    passwordMatchError.setVisibility(View.VISIBLE);
                    registerPass.setText("");
                    registerPassAgain.setText("");
                } else if (!usernameR.equals("") && passwordR1.length() > 0) {
                    if (!UserRecords.containsUsername(usernameR)) {
                        User newUser = new User(usernameR, passwordR1);
                        UserRecords.add_user((newUser));
                        registerPass.setText("");
                        registerPassAgain.setText("");
                        registerName.setText("");
                        success.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                            }
                        }, 1300);
                    }


                }
            }
        });

        registerPass.setOnClickListener(new View.OnClickListener() { //doesn't work really. requires two taps
            @Override
            public void onClick(View view) {
                passwordMatchError.setVisibility(View.INVISIBLE);
                usernameError.setVisibility(View.INVISIBLE);
            }
        });

    }

}

