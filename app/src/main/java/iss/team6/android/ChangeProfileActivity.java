package iss.team6.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeProfileActivity extends AppCompatActivity {

    EditText newUsername;
    EditText newPassword;

    Button savenewdeets;

    public ChangeProfileActivity(){
        //Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeprofile);

        newUsername = findViewById(R.id.newUsername);
        newPassword = findViewById(R.id.newPassword);
        savenewdeets = findViewById(R.id.savenewdeets);

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        if (pref.contains("username") && pref.contains("password")) {
            boolean loginOk = logIn(pref.getString("username", ""), pref.getString("password", ""));
            if (loginOk) {
                startProtectedActivity();
            }
        }

        savenewdeets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newusername = newUsername.getText().toString();
                String newpassword = newPassword.getText().toString();

                boolean loginOk = logIn(newusername, newpassword);
                if (loginOk) {
                    SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", newusername);
                    editor.putString("password", newpassword);
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Userprofile updated successfully!", Toast.LENGTH_SHORT).show();
                    startProtectedActivity();
                }
            }
        });
    }

    private boolean logIn(String username, String password) {
        if (username.equals("Goodevening") && password.equals("Singapore")){
            return true;
        }
        return false;
    }

    private void startProtectedActivity(){
        Intent intent = new Intent(this, HomeDashboardFragment.class);
        startActivity(intent);
    }

}