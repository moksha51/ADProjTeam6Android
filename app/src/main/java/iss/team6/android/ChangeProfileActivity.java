package iss.team6.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeProfileActivity extends AppCompatActivity {

    EditText newUsername;
    EditText newPassword;

    Button savenewdeets;
    String newusername = "";
    String newpassword = "";

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
        savenewdeets.setTextColor(Color.RED);

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        if (pref.contains("username") && pref.contains("password")) {
            savenewdeets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newusername = newUsername.getText().toString();
                    newpassword = newPassword.getText().toString();

                    if (newusername != null && newpassword != null){
                        savenewdeets.setText("SAVED");
                        savenewdeets.setTextColor(Color.GREEN);
                    }

                    SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", newusername);
                    editor.putString("password", newpassword);
                    editor.commit();
                    finish();

                    Toast.makeText(ChangeProfileActivity.this, "Userprofile updated successfully!", Toast.LENGTH_SHORT).show();
                    startProtectedActivity();
                }
            });
        }
    }

    private void startProtectedActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}