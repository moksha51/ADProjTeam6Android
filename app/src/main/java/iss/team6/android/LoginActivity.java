package iss.team6.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    Button login;
    Button createaccount;
    Button fblogin;

    EditText username;
    EditText password;

    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fragment);

        //For Facebook login
        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && accessToken.isExpired() == false) {
            startProtectedActivity();
            finish();
        }


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startProtectedActivity();
                finish();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        // For Facebook Login

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        fblogin = findViewById(R.id.fblogin);
        login = findViewById(R.id.login);
        createaccount = findViewById(R.id.createaccount);

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        if (pref.contains("username") && pref.contains("password")) {
            boolean loginOk = logIn(pref.getString("username", ""), pref.getString("password", ""));
            if (loginOk) {
                startProtectedActivity();
            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString();
                String Password = password.getText().toString();

                boolean loginOk = logIn(Username, Password);
                if (loginOk) {
                    SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", Username);
                    editor.putString("password", Password);
                    editor.commit();
                    finish();

                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                    startProtectedActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Login! Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString();
                String Password = password.getText().toString();

                boolean createOk = createAcct(Username, Password);
                if (createOk) {
                    SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", Username);
                    editor.putString("password", Password);
                    editor.commit();
                    finish();

                    Toast.makeText(getApplicationContext(), "New account created!", Toast.LENGTH_SHORT).show();
                    startProtectedActivity();
                }
            }
        });

        fblogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login to facebook
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
            }
        });
    }

    private boolean logIn(String username, String password) {
        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        pref.getString("username", username);
        pref.getString("password", password);
        //"Goodmorning" and "Singapore" below should be equals to JSON object database username and password
        if (username.equals("GM") && password.equals("SG")) {

            return true;
        }
        return false;
    }

    private boolean createAcct(String username, String password) {
        if (username.equals("Goodmorning") && password.equals("Singapore")) {
            return true;
        }
        return false;
        //Need to write method to create acct in database. Not sure how cos no database?
//     private boolean createAcct(String username, String password) {
//         DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
//         String email_id = email.getText().toString();
//         boolean dbHelper.isExist(email_id);
//     }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void startProtectedActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}


