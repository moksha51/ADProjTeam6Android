package iss.team6.android;

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

import androidx.fragment.app.Fragment;

public class Changeusername_fragment extends Fragment {

    EditText oldusername;
    EditText newusername;

    TextView oldusertext;
    TextView newusertext;
    TextView newuserinfo;
    //TextView newuserpassword;

    Button savenewuser;

    public Changeusername_fragment(){
        //Reqyured empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_changeusername_fragment, container, false);

        newusername = view.findViewById(R.id.newusername);
        oldusername = view.findViewById(R.id.oldusername);
        savenewuser = view.findViewById(R.id.savenewuser);
        String newuserpassword = "Singapore";


        savenewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Newusername = newusername.getText().toString();
                String Oldusername = oldusername.getText().toString();
                //String Newuserpassword = newuserpassword.getText().toString();

                boolean loginOk = logIn(Newusername, newuserpassword);
                if (loginOk) {
                    SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", Newusername);
                    editor.putString("password", newuserpassword);
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Username updated successfully!", Toast.LENGTH_SHORT).show();
                    startProtectedActivity();
                }
            }
        });
        return view;
    }

    // For when the database is ready
//    private boolean changeusername(String Oldusername, String Newusername){
//        if (Newusername != Oldusername){
//
//        }
//    }

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
