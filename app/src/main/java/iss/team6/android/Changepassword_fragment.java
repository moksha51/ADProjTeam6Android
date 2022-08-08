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

public class Changepassword_fragment extends Fragment {

    EditText oldpassword;
    EditText newpassword;

    TextView oldpasstext;
    TextView newpasstext;
    TextView newpassinfo;
    //TextView newpassusername;

    Button savenewpass;


    public Changepassword_fragment(){
        //Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_changepassword_fragment, container, false);
        newpassword = view.findViewById(R.id.newpassword);
        oldpassword = view.findViewById(R.id.oldpassword);
        savenewpass = view.findViewById(R.id.savenewpass);
        String newpassusername = "Goodmorning";


        savenewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Newpassword = newpassword.getText().toString();
                //String Oldpassword = oldpassword.getText().toString();
                //String Newuserpassword = newuserpassword.getText().toString();

                boolean loginOk = logIn(newpassusername, Newpassword);
                if (loginOk) {
                    SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", newpassusername);
                    editor.putString("password", Newpassword);
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Username updated successfully!", Toast.LENGTH_SHORT).show();
                    startProtectedActivity();
                }
            }
        });

        return view;
    }

    // For when the database is ready
//    private boolean changepassword(String Oldpassword, String Newpassword){
//        if (Newpassword != Oldpassword){
//
//        }
//    }

    private boolean logIn(String username, String password) {
        if (username.equals("Goodmorning") && password.equals("team6")){
            return true;
        }
        return false;
    }

    private void startProtectedActivity(){
        Intent intent = new Intent(this, HomeDashboardFragment.class);
        startActivity(intent);
    }

}
