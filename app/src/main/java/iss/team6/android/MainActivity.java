package iss.team6.android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Intent intent = null;

    public static final String EXTERNAL_URL = "externalUrl";
    BottomNavigationView bottomNavigationView;
    View fragmentContainer;
    private static final int REQUEST_CODE = 100;
    CallbackManager callbackManager;
    static final String apiendpoint = "https://filesamples.com/samples/code/json/sample2.json";
    String username = "";
    int monCount;
    int tueCount;
    int wedCount;
    int thuCount;
    int friCount;
    int satCount;
    int sunCount;
    int glassCount;
    int metalCount;
    int plasticCount;
    int paperCount;
    String url = apiendpoint + username;

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
        if (alrLoggedInOk) {
            bottomNavigationView = findViewById(R.id.navMenu);
            fragmentContainer = findViewById(R.id.fragment_container);
            replaceFragment(new HomeDashboardFragment());
        } else {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.homeFragment:
                    getDayCount();
                    replaceFragment(new HomeDashboardFragment());
                    break;
                case R.id.mapFragment:
                    String externalUrl =
                            "https://www.nea.gov.sg/our-services/waste-management/3r-programmes-and-resources/recycling-collection-points";
                    launchExternalPage(externalUrl);
                    break;
                case R.id.cameraFragment:
                    intent = new Intent(this, CameraActivity.class);
                    startActivity(intent);
                    break;
                case R.id.statsFragment:
                    replaceFragment(new StatsFragment());
                    getTypeCount();
                    break;
                case R.id.profileFragment:
                    replaceFragment(new UserProfileFragment());
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

    private boolean alreadyLoggedIn() {

        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        SharedPreferences pref = getSharedPreferences("user_credentials",Context.MODE_PRIVATE);
        if (accessToken != null && accessToken.isExpired() == false) {
            return true;
        } else if (pref != null) {
            if (pref.contains("username") && pref.contains("password")) {
                return true;
            }
        }
        return false;
    }

    public void getDayCount(){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        final String apiendpoint = "https://filesamples.com/samples/code/json/sample2.json";
        JsonObjectRequest jsonOjectRequest = new JsonObjectRequest(Request.Method.GET, apiendpoint, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    monCount = response.getInt("age");
                     String abc = response.getString("firstName");
//                    tueCount = response.getInt("age");
//                    wedCount = response.getInt("age");
//                    thuCount = response.getInt("age");
//                    friCount = response.getInt("age");
//                    satCount = response.getInt("age");
//                    sunCount = response.getInt("age");
                    Toast.makeText(MainActivity.this, abc + monCount, Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = getSharedPreferences("dayCount", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("monCount", monCount);
//                    editor.putInt("tueCount", tueCount);
//                    editor.putInt("wedCount", wedCount);
//                    editor.putInt("thuCount", thuCount);
//                    editor.putInt("friCount", friCount);
//                    editor.putInt("satCount", satCount);
//                    editor.putInt("sunCount", sunCount);
                    editor.commit();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonOjectRequest);
    }

    public void getTypeCount(){

    }
}