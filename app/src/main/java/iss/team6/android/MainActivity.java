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

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Intent intent = null;

    public static final String EXTERNAL_URL = "externalUrl";
    BottomNavigationView bottomNavigationView;
    View fragmentContainer;
    private static final int REQUEST_CODE = 100;
    CallbackManager callbackManager;
    String username;
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
                    Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
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
                Locale locale = new Locale("EN", "SG");
                int weekOfYear = LocalDate
                        .now()
                        .get(WeekFields.of(locale).weekOfYear());
                LocalDate firstDayofWeek = LocalDate
                        .now()
                        .with(WeekFields.of(locale).getFirstDayOfWeek())
                        .with(WeekFields.of(locale).weekOfWeekBasedYear(), weekOfYear);
                //Populating hashmap of weekly statistics

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                //username "Halim" is hardcoded in case API endpoint is no longer in service
                username = "Halim";
                //uncomment below code to test API endpint
                //username = getSharedPreferences("user_credentials", Context.MODE_PRIVATE).getString("username", username);
                String url = "localhost:8080/api/weeklyuserstats?username=";
                url += username;

                JsonObjectRequest jsonOjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            int [] weekDay = new int [7];
//                            for (int i = 1; i < 8; i++) {
//                                LocalDate currentDay = firstDayofWeek.plusDays(i);
//                                weekDay[i] = response.getJSONObject(currentDay.toString()).getInt("metalTypeCount") +
//                                        response.getJSONObject(currentDay.toString()).getInt("glassTypeCount") +
//                                        response.getJSONObject(currentDay.toString()).getInt("paperTypeCount") +
//                                        response.getJSONObject(currentDay.toString()).getInt("plasticTypeCount");
//                            }
//                            monCount = weekDay[1];
//                            tueCount = weekDay[2];
//                            wedCount = weekDay[3];
//                            thuCount = weekDay[4];
//                            friCount = weekDay[5];
//                            satCount = weekDay[6];
//                            sunCount = weekDay[7];

                            //hardcoding below values in case API endpoint is no longer in service

                            response.getJSONObject("");

                            monCount = 100;
                            tueCount = 90;
                            wedCount = 80;
                            thuCount = 70;
                            friCount = 60;
                            satCount = 50;
                            sunCount = 40;

                            Toast.makeText(MainActivity.this, "test" , Toast.LENGTH_SHORT).show();
                            SharedPreferences pref = getSharedPreferences("dayCount", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            //Dear Tin, putting into SharedPreferences so that user can view stats without internet.

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
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                //username "Halim" is hardcoded in case API endpoint is no longer in service
                username = "Halim";
                //uncomment below code to test API endpint
                //username = getSharedPreferences("user_credentials", Context.MODE_PRIVATE).getString("username", username);
                String url = "localhost:8080/api/alluserstats?username=";
                url += username;
                JsonObjectRequest jsonOjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            glassCount = response.getJSONObject(username).getInt("glassTypeCount");
//                            metalCount = response.getJSONObject(username).getInt("metalTypeCount");
//                            paperCount = response.getJSONObject(username).getInt("paperTypeCount");
//                            plasticCount = response.getJSONObject(username).getInt("plasticTypeCount");
                            response.getJSONObject("");

//                            hardcoding below values in case API endpoint is no longer in service
                            glassCount = 100;
                            metalCount = 90;
                            paperCount = 80;
                            plasticCount = 70;

                        //Dear Tin, putting into SharedPreferences so that user can view stats without internet.
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