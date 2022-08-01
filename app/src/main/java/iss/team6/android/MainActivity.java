package iss.team6.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    View fragmentContainer;
    WebView webView;
    private final static String EXTERNAL_URL = "externalUrl";
    private final static String mapUrl = "https://www.nea.gov.sg/our-services/waste-management/3r-programmes-and-resources/recycling-collection-points";
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navMenu);
        fragmentContainer = findViewById(R.id.fragment_container);
        replaceFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.homeFragment:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.mapFragment:
                    /*Intent intent = new Intent(MainActivity.this, MapFragment.class);
                    intent.putExtra(EXTERNAL_URL,mapUrl);
                    startActivity(intent);
                    webView = findViewById(R.id.web_view);
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(mapUrl);*/
                    replaceFragment(new MapFragment());
                    break;
                case R.id.cameraFragment:
                    replaceFragment(new CameraFragment());
                    break;
                case R.id.statsFragment:
                    replaceFragment(new StatsFragment());
                    break;
                case R.id.profileFragment:
                    replaceFragment(new ProfileFragment());
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
}

        /*Button enableCamera = findViewById(R.id.btn_camera_take_photo);
        enableCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCameraPermission()) {
                    enableCamera();
                } else {
                    requestPermission();
                }
            }
        });
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    private void enableCamera() {
        Intent intent = new Intent(this, CameraFragment.class);
        startActivity(intent);
    }*/