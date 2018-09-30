package edu.gatech.cs2340.app_4t5.models;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import java.util.HashMap;
import java.util.Map;
import static android.content.ContentValues.TAG;

/**
 * Notes: must call close() when done using this object.
 * TODO: fix username exploit in node.js
 */
public class Authentication {
    private FirebaseFunctions m_functions;
    private FirebaseAuth m_auth;
    private User m_user;
    private final int TOKEN_DURATION = 60*60*1000;  // 1 hour
    private final int REFRESH_INTERVAL = TOKEN_DURATION - 2*60*1000; // TOKEN_DURATION - 2 minutes
    private Handler m_handler;
    private Runnable m_status_checker;


    public void close() {
        stopRepeatingTask();
    }

    void startRepeatingTask() {
        m_status_checker.run();
    }

    void stopRepeatingTask() {
        m_handler.removeCallbacks(m_status_checker);
    }

    public Authentication() {
        m_functions = FirebaseFunctions.getInstance();
        m_auth = FirebaseAuth.getInstance();

        m_handler = new Handler();
        m_status_checker = new Runnable() {
            @Override
            public void run() {
                // TODO: get new token here
                m_handler.postDelayed(m_status_checker, REFRESH_INTERVAL);
            }
        };
        startRepeatingTask();
    }

    /**
     *
     * @param username
     * @param password
     * @return a Task which is true if the user has logged in and false if their login attempt failed.
     */
    public Task<Boolean> sign_in(String username, String password) {
        return getToken(username, password).continueWith(new Continuation<String, Boolean>() {
            @Override
            public Boolean then(@NonNull Task<String> task) {
                String token = task.getResult();
                if (token == "") {
                    return false;
                }
                return m_auth.signInWithCustomToken(token)
                        .continueWith(new Continuation<AuthResult, Boolean>() {
                            public Boolean then(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = m_auth.getCurrentUser();
                                    DatabaseReference tmp_db = FirebaseDatabase.getInstance().getReference();
                                    tmp_db.child("inventory").child("auth_test").setValue("YES!");
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }).getResult();
            }
        });
    }
    /*
    public void sign_in(String username, String password) {
        getToken(username, password).continueWith(new Continuation<String, Object>() {
            @Override
            public Object then(@NonNull Task<String> task) throws Exception {
                String token = task.getResult();
                m_auth.signInWithCustomToken(token)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithCustomToken:success");
                                    FirebaseUser user = m_auth.getCurrentUser();
                                    // do something with update
                                    // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                    // do something with update
                                    // updateUI(null);
                                }
                            }
                        });
                return null;
            }
        });
    }*/

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
