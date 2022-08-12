package iss.team6.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTERNAL_URL = "externalUrl";
    BottomNavigationView bottomNavigationView;
    View fragmentContainer;
    private static final int REQUEST_CODE = 100;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_CODE);
        }

        boolean alrLoggedInOk = alreadyLoggedIn();
        if(alrLoggedInOk) {
            bottomNavigationView = findViewById(R.id.navMenu);
            fragmentContainer = findViewById(R.id.fragment_container);
            replaceFragment(new HomeFragment());
        }
        else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        //testing text 12345

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.homeFragment:
                    replaceFragment(new HomeDashboardFragment());
                    break;
                case R.id.mapFragment:
                    String externalUrl =
                            "https://www.nea.gov.sg/our-services/waste-management/3r-programmes-and-resources/recycling-collection-points";
                    launchExternalPage(externalUrl);
                    break;
                case R.id.cameraFragment:
                    Intent intent = new Intent(this, CameraActivity.class);
                    startActivity(intent);
                    break;
                case R.id.statsFragment:
                    replaceFragment(new StatsFragment());
                    break;
                case R.id.profileFragment:
                    Intent intent = new Intent(this, UserprofileActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_container, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }

    void launchExternalPage(String externalUrl) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra(EXTERNAL_URL, externalUrl);
        startActivity(intent);
    }

    private boolean alreadyLoggedIn(){

        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        if (accessToken != null && accessToken.isExpired() == false) {
            return true;
        }
        else if (pref != null){
            return true;
        }
        return false;
    }



}