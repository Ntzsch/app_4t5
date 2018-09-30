package edu.gatech.cs2340.app_4t5.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.cs2340.app_4t5.R;
import edu.gatech.cs2340.app_4t5.models.User;
import edu.gatech.cs2340.app_4t5.models.UserRecords;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {
    private EditText username_text;
    private EditText password_text;
    private Button login_button;
    private TextView register_button;
    private TextView login_error_view;
    private FirebaseAuth m_auth;
    private FirebaseFunctions m_functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TEMPORARY
        m_auth = FirebaseAuth.getInstance();
        m_functions = FirebaseFunctions.getInstance();
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
                login_button.setEnabled(false);


                sign_in(username, password).continueWith(new Continuation<Boolean, Object>() {
                    @Override
                    public Object then(Task<Boolean> task){
                        login_button.setEnabled(true);
                        if (task.getResult() == true) {
                            // DatabaseReference tmp_db = FirebaseDatabase.getInstance().getReference();
                            // tmp_db.child("inventory").child("auth_test").setValue("YES!");
                            login_error_view.setVisibility(View.INVISIBLE);
                            username_text.setText("");
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } else {
                            login_error_view.setVisibility(View.VISIBLE);
                        }
                        return null;
                    }
                });

                /*
                tmp_auth.getToken("user1", "pass1").continueWith(new Continuation<String, Object>() {
                    @Override
                    public Object then(@NonNull Task<String> task) throws Exception {
                        username_text.setText(task.getResult());
                        login_button.setEnabled(true);
                        return null;
                    }
                });

                /*
                tmp_auth.tester().continueWith(new Continuation<String, Object>() {
                    @Override
                    public Object then(@NonNull Task<String> task) throws Exception {
                        Log.d(TAG, task.getResult());
                        username_text.setText(task.getResult());
                        login_button.setEnabled(true);
                        return null;
                    }
                });
                // tmp_auth.sign_in("user1", "pass1");

                /*
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
                */
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
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = m_auth.getCurrentUser();
        // update UI
    }

    private void signInAnonymously() {
        // [START signin_anonymously]
        m_auth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = m_auth.getCurrentUser();
                            update(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            update(null);
                        }
                    }
                });
        // [END signin_anonymously]
    }

    private void signOut() {
        m_auth.signOut();
        update(null);
    }

    private void update(FirebaseUser user) {
        boolean is_logged_in = user != null;

    }

    public Task<Boolean> sign_in(String username, String password) {
        return getToken(username, password).continueWith(new Continuation<String, Boolean>() {
            @Override
            public Boolean then(@NonNull Task<String> task) {
                String token = task.getResult();
                Log.d(TAG, token);
                Log.d("test", "yet another test");
                Log.d("test2:", token);
                if (token.equals("")) {
                    return false;
                }
                return m_auth.signInWithCustomToken(token)
                        .continueWith(new Continuation<AuthResult, Boolean>() {
                            public Boolean then(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = m_auth.getCurrentUser();
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }).getResult();
            }
        });
    }

    public Task<String> getToken(String username, String passHash) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("passHash", passHash);
        return m_functions
                .getHttpsCallable("getToken")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() { // tokens are strings
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        String token = (String) task.getResult().getData();
                        return token;
                    }
                });
    }
}

