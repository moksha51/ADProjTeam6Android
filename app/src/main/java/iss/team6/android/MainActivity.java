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
    int min = 50;
    int max = 100;

    int glassCount;
    int metalCount;
    int plasticCount;
    int paperCount;
    String url = apiendpoint + username;
    Bundle bundle;

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
                    Toast.makeText(this, "Refreshing stats", Toast.LENGTH_SHORT).show();
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
                    getTypeCount();
                    replaceFragment(new StatsFragment());
                    Toast.makeText(this, "Refreshing Stats", Toast.LENGTH_SHORT).show();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                //int monCount = (int)Math.floor(Math.random()*(max-min+1)+min);
                int tueCount= (int)Math.floor(Math.random()*(max-min+1)+min);;
                int wedCount= (int)Math.floor(Math.random()*(max-min+1)+min);;
                int thuCount= (int)Math.floor(Math.random()*(max-min+1)+min);;
                int friCount= (int)Math.floor(Math.random()*(max-min+1)+min);;
                int satCount= (int)Math.floor(Math.random()*(max-min+1)+min);;
                int sunCount= (int)Math.floor(Math.random()*(max-min+1)+min);;
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                final String apiendpoint = "https://filesamples.com/samples/code/json/sample2.json";
                JsonObjectRequest jsonOjectRequest = new JsonObjectRequest(Request.Method.GET, apiendpoint, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int monCount = response.getInt("age");
                            String abc = response.getString("firstName");
//                            tueCount = response.getInt("age");
//                            wedCount = response.getInt("age");
//                            thuCount = response.getInt("age");
//                            friCount = response.getInt("age");
//                            satCount = response.getInt("age");
//                            sunCount = response.getInt("age");
                            Toast.makeText(MainActivity.this, abc + monCount , Toast.LENGTH_SHORT).show();
                            SharedPreferences pref = getSharedPreferences("dayCount", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("monCount", monCount);
                            editor.putInt("tueCount", tueCount);
                            editor.putInt("wedCount", wedCount);
                            editor.putInt("thuCount", thuCount);
                            editor.putInt("friCount", friCount);
                            editor.putInt("satCount", satCount);
                            editor.putInt("sunCount", sunCount);
                            editor.apply();
                            replaceFragment(new HomeDashboardFragment());
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
        }).start();
    }

    public void getTypeCount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                glassCount = (int)Math.floor(Math.random()*(max-min+1)+min);
                metalCount= (int)Math.floor(Math.random()*(max-min+1)+min);;
                plasticCount= (int)Math.floor(Math.random()*(max-min+1)+min);;
                paperCount= (int)Math.floor(Math.random()*(max-min+1)+min);;
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                final String apiendpoint = "https://filesamples.com/samples/code/json/sample2.json";
                JsonObjectRequest jsonOjectRequest = new JsonObjectRequest(Request.Method.GET, apiendpoint, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            glassCount = response.getInt("age");
                            String abc = response.getString("firstName");
//                            tueCount = response.getInt("age");
//                            wedCount = response.getInt("age");
//                            thuCount = response.getInt("age");
//                            friCount = response.getInt("age");
//                            satCount = response.getInt("age");
//                            sunCount = response.getInt("age");
                            //Toast.makeText(MainActivity.this, abc + glassCount , Toast.LENGTH_SHORT).show();
                            SharedPreferences pref = getSharedPreferences("trashTypeCount", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("glassCount", glassCount);
                            editor.putInt("metalCount", metalCount);
                            editor.putInt("paperCount", paperCount);
                            editor.putInt("plasticCount", plasticCount);
                            editor.apply();
                            replaceFragment(new StatsFragment());
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
        }).start();
    }
}