package iss.team6.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    EditText ETusername;
    EditText ETpassword;
    CallbackManager callbackManager;
    String usernameText;
    String passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //For Facebook login
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startProtectedActivity();
                        finish();
                    }
                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Try logging into Facebook again", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, "Try logging into Facebook again", Toast.LENGTH_SHORT).show();
                    }
                });
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        if (accessToken != null && accessToken.isExpired() == false) {
//            startProtectedActivity();
//            finish();
//        }

        // For Facebook Login
        ETusername = findViewById(R.id.username);
        ETpassword = findViewById(R.id.password);
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
                usernameText = ETusername.getText().toString();
                passwordText = ETpassword.getText().toString();

                boolean loginOk = logIn(usernameText, passwordText);
                if (loginOk) {
                    SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", usernameText);
                    editor.putString("password", passwordText);
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
                usernameText = ETusername.getText().toString();
                passwordText = ETpassword.getText().toString();

                boolean createOk = createAcct(usernameText, passwordText);
                if (createOk) {
                    SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", usernameText);
                    editor.putString("password", passwordText);
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

    private boolean logIn(String usernameText, String passwordText) {
        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        pref.getString("username", usernameText);
        pref.getString("password", passwordText);
        //Tin, hardcoded for your username and password for your convenience

        if (usernameText.equals("GM1") || usernameText.equals("Halim"))
        {
            if (passwordText.equals("SG1") || passwordText.equals("halim")){
                return true;
            }
        }
        return false;
    }

    private boolean createAcct(String usernameText, String passwordText) {
        usernameText = ETusername.getText().toString();
        passwordText = ETpassword.getText().toString();
        try {
            SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("username", usernameText);
            editor.putString("password", passwordText);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
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