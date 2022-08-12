package iss.team6.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;


public class UserprofileActivity extends AppCompatActivity {

    TextView user;
    Button btnlogout;
    Button btnchangeuserprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        // View view = inflater.inflate(R.layout.activity_userprofile, container, false);
        setContentView(R.layout.activity_userprofile);

        btnlogout = findViewById(R.id.btnlogout);
        btnchangeuserprofile = findViewById(R.id.btnchangeuserprofile);
        user = findViewById(R.id.user);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

//        GraphRequest request = GraphRequest.newMeRequest(
//                accessToken,
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//
//                        // Application code
//                        try {
//                            String fullName = object.getString("name");
//                            user.setText(fullName);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
//        request.setParameters(parameters);
//        request.executeAsync();

        btnchangeuserprofile.setOnClickListener(new View.OnClickListener() {
            // @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserprofileActivity.this, ChangeProfileActivity.class);
                startActivity(intent);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener()  {
            //@RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                SharedPreferences pref = getSharedPreferences("user_credentials",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                finish();
                Intent intent = new Intent(UserprofileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}