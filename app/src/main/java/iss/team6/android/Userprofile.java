package iss.team6.android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Userprofile extends AppCompatActivity {

    TextView user;
    TextView datejoined;

    Button btnlogout;
    Button btnchangeuserprofile;

    public Userprofile() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnchangeuserprofile = (Button) findViewById(R.id.btnchangeuserprofile);

        user = (TextView) findViewById(R.id.user);
        datejoined = (TextView) findViewById(R.id.datejoined);


        btnlogout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Userprofile.this, LoginFragment.class);
                startActivity(intent);
            }
        });

        btnchangeuserprofile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Userprofile.this, changeprofile.class);
                startActivity(intent);
            }
        });

    }


}