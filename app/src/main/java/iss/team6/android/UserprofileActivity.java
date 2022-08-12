package iss.team6.android;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginFragment;
import com.facebook.login.LoginManager;


import org.json.JSONException;
import org.json.JSONObject;


public class UserprofileActivity extends AppCompatActivity {

    TextView user;

    Button btnlogout;
    Button btnchangeuserprofile;

    public UserprofileActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        // View view = inflater.inflate(R.layout.activity_userprofile, container, false);


        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnchangeuserprofile = (Button) findViewById(R.id.btnchangeuserprofile);
        user = (TextView) findViewById(R.id.user);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        // Application code
                        try {
                            String fullName = object.getString("name");
                            user.setText(fullName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();

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
                SharedPreferences pref = getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(UserprofileActivity.this, LoginFragment.class);
                startActivity(intent);
            }
        });

        //return view;
        //}
    }

}