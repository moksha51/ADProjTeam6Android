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
    String username = "";
    String usernameText = "";
    
    int glassCount;
    int metalCount;
    int plasticCount;
    int paperCount;
    int monCountPl;
    int tueCountPl;
    int wedCountPl;
    int thuCountPl;
    int friCountPl;
    int satCountPl;
    int sunCountPl;
    int monCountGl;
    int tueCountGl;
    int wedCountGl;
    int thuCountGl;
    int friCountGl;
    int satCountGl;
    int sunCountGl;
    int monCountMe;
    int tueCountMe;
    int wedCountMe;
    int thuCountMe;
    int friCountMe;
    int satCountMe;
    int sunCountMe;
    int monCountPa;
    int tueCountPa;
    int wedCountPa;
    int thuCountPa;
    int friCountPa;
    int satCountPa;
    int sunCountPa;

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
        getDayCount();

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
        //Dear Tin, putting into SharedPreferences so that user can view stats without internet.
        SharedPreferences pref = getSharedPreferences("dayCount", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

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

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                //uncomment below code to test API endpint
                //usernameText = getSharedPreferences("user_credentials", Context.MODE_PRIVATE).getString("username", username);
                String url = "http://167.71.201.46:6868/api/weeklyuserstats?username=Halim";

                //hardcoded endpoint for convenience
                //can change Halim to Heily or Yeemon etc depending on who logged in
                //url += usernameText;

                JsonObjectRequest jsonOjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                                                try {
                            int [] weekDayPl = new int [7];
                            int [] weekDayGl = new int [7];
                            int [] weekDayPa = new int [7];
                            int [] weekDayMe = new int [7];
                            for (int i = 0; i < 7; i++) {
                                LocalDate currentDay = firstDayofWeek.plusDays(i);
                                weekDayPl[i] = response.getJSONObject(currentDay.toString()).getInt("plasticTypeCount");
                                weekDayGl[i] = response.getJSONObject(currentDay.toString()).getInt("glassTypeCount");
                                weekDayPa[i] = response.getJSONObject(currentDay.toString()).getInt("paperTypeCount");
                                weekDayMe[i] = response.getJSONObject(currentDay.toString()).getInt("metalTypeCount");
                            }

                            monCountPl = weekDayPl[1];
                            tueCountPl = weekDayPl[2];
                            wedCountPl = weekDayPl[3];
                            thuCountPl = weekDayPl[4];
                            friCountPl = weekDayPl[5];
                            satCountPl = weekDayPl[6];
                            sunCountPl = weekDayPl[0];

                            monCountGl = weekDayGl[1];
                            tueCountGl = weekDayGl[2];
                            wedCountGl = weekDayGl[3];
                            thuCountGl = weekDayGl[4];
                            friCountGl = weekDayGl[5];
                            satCountGl = weekDayGl[6];
                            sunCountGl = weekDayGl[0];

                            monCountMe = weekDayMe[1];
                            tueCountMe = weekDayMe[2];
                            wedCountMe = weekDayMe[3];
                            thuCountMe = weekDayMe[4];
                            friCountMe = weekDayMe[5];
                            satCountMe = weekDayMe[6];
                            sunCountMe = weekDayMe[0];

                            monCountPa = weekDayPa[1];
                            tueCountPa = weekDayPa[2];
                            wedCountPa = weekDayPa[3];
                            thuCountPa = weekDayPa[4];
                            friCountPa = weekDayPa[5];
                            satCountPa = weekDayPa[6];
                            sunCountPa = weekDayPa[0];

                            editor.putInt("monCountPl", monCountPl);
                            editor.putInt("monCountPa", monCountPa);
                            editor.putInt("monCountMe", monCountMe);
                            editor.putInt("monCountGl", monCountGl);

                            editor.putInt("tueCountPl", tueCountPl);
                            editor.putInt("tueCountPa", tueCountPa);
                            editor.putInt("tueCountMe", tueCountMe);
                            editor.putInt("tueCountGl", tueCountGl);

                            editor.putInt("wedCountPl", wedCountPl);
                            editor.putInt("wedCountPa", wedCountPa);
                            editor.putInt("wedCountMe", wedCountMe);
                            editor.putInt("wedCountGl", wedCountGl);

                            editor.putInt("thuCountPl", thuCountPl);
                            editor.putInt("thuCountGl", thuCountGl);
                            editor.putInt("thuCountPa", thuCountPa);
                            editor.putInt("thuCountMe", thuCountMe);

                            editor.putInt("friCountPl", friCountPl);
                            editor.putInt("friCountPa", friCountPa);
                            editor.putInt("friCountMe", friCountMe);
                            editor.putInt("friCountGl", friCountGl);

                            editor.putInt("satCountPl", satCountPl);
                            editor.putInt("satCountMe", satCountMe);
                            editor.putInt("satCountGl", satCountGl);
                            editor.putInt("satCountPa", satCountPa);

                            editor.putInt("sunCountPl", sunCountPl);
                            editor.putInt("sunCountMe", sunCountMe);
                            editor.putInt("sunCountGl", sunCountGl);
                            editor.putInt("sunCountPa", sunCountPa);
                                                    
                            editor.commit();
                            replaceFragment(new HomeDashboardFragment());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Test" + error, Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(jsonOjectRequest);
            }
        }).start();


    }

    public void getTypeCount(){

        //Dear Tin, putting into SharedPreferences so that user can view stats without internet.
        SharedPreferences pref = getSharedPreferences("trashTypeCount", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                //uncomment below code to test API endpint
                //usernameText = getSharedPreferences("user_credentials", Context.MODE_PRIVATE).getString("username", username);

                String url = "http://167.71.201.46:6868/api/alluserstats?username=Halim";

                //hardcoded endpoint for convenience
                //can change Halim to Heily or Yeemon etc depending on who logged in
                //url += usernameText;

                JsonObjectRequest jsonOjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            glassCount = response.getInt("glassTypeCount");
                            metalCount = response.getInt("metalTypeCount");
                            paperCount = response.getInt("paperTypeCount");
                            plasticCount = response.getInt("plasticTypeCount");
                            editor.clear();
                            editor.putInt("glassCount", glassCount);
                            editor.putInt("metalCount", metalCount);
                            editor.putInt("paperCount", paperCount);
                            editor.putInt("plasticCount", plasticCount);
                            editor.commit();
                            replaceFragment(new StatsFragment());
                        } catch (JSONException e)  {
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
