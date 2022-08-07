package iss.team6.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class UserprofileFragment extends Fragment {

    TextView User;
    TextView Datejoined;

    Button Logout;
    Button Changeusername;
    Button Changepassword;

    public UserprofileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_userprofile_fragment, container, false);

        User = (TextView) view.findViewById(R.id.user);
        Datejoined = (TextView) view.findViewById(R.id.datejoined);

        Logout = (Button) view.findViewById(R.id.btnlogout);
        Changeusername = (Button) view.findViewById(R.id.btnchangeusername);
        Changepassword = (Button) view.findViewById(R.id.btnchangepassword);


        Logout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(this, LoginFragment.class);
                startActivity(intent);
            }
        });

        Changeusername.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(changeusername_fragment.class);
                startActivity(intent);
            }
        });

        Changepassword.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(this, changepassword_fragment.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
