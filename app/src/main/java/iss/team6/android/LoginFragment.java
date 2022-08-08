package iss.team6.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    Button login;
    Button createaccount;

    EditText username;
    EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        createaccount = findViewById(R.id.createaccount);

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        if (pref.contains("username") && pref.contains("password")) {
            boolean loginOk = logIn(pref.getString("username", ""),pref.getString("password", ""));
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

                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                    startProtectedActivity();
                } else {
                    Toast.makeText(getApplicationContext(),"Invalid Login! Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        private boolean logIn(String username, String password){
            if (username.equals("Goodmorning") && password.equals("Singapore")){
                return true;
            }
            return false;
        }

        createaccount.setOnClickListener(new View.OnClickListener(){
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

                    Toast.makeText(getApplicationContext(), "New account created!", Toast.LENGTH_SHORT).show();
                    startProtectedActivity();
                }
            }
        });
    }

//     private boolean createAcct(String username, String password) {
//         DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
//         String email_id = email.getText().toString();
//         boolean dbHelper.isExist(email_id);
//     }


    private void startProtectedActivity(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

}
