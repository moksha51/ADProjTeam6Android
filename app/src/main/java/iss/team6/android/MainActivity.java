package iss.team6.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    View fragmentContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navMenu);
        fragmentContainer = findViewById(R.id.fragment_container);
        replaceFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch(item.getItemId()){

                case R.id.homeFragment:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.mapFragment:
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

    private void replaceFragment(Fragment fragment){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_container,fragment);
        trans.addToBackStack(null);
        trans.commit();
    }
}