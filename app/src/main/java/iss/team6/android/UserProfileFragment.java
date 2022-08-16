package iss.team6.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;


public class UserProfileFragment extends Fragment {

    public UserProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_userprofile, container, false);
        Button btnlogout = view.findViewById(R.id.btnlogout);
        Button btnchangeuserprofile = view.findViewById(R.id.btnchangeuserprofile);
        TextView user = view.findViewById(R.id.user);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
//        request.setParameters(parameters);
//        request.executeAsync();

        btnchangeuserprofile.setOnClickListener(new View.OnClickListener() {
            // @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangeProfileActivity.class);
                startActivity(intent);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener()  {
            //@RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                SharedPreferences pref = getActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}