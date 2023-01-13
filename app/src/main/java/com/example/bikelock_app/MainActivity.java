package com.example.bikelock_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bikelock_app.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Vibrator vibe;
    private static final int videRate = 30;
    Button button;
    FragmentManager fragmentManager;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        HomeFragment homePage = HomeFragment.newInstance(database);

        myRef = database.getReference(
                "/ParkingLot/Lot_001/Alarm");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if(value.toString() == "true")
                {
                    Toast.makeText(MainActivity.this, "!!!你車被偷了!!!", Toast.LENGTH_LONG).show();
                }
                Log.d("TAG", "Value is: " + value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        replaceFragment(homePage);
        Controller.getInstance().setMainActivity(this);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.menu_Home:
                    vibe.vibrate(videRate);
                    replaceFragment(homePage);
                    break;
                case R.id.menu_Location:
                    vibe.vibrate(videRate);
                    replaceFragment(new ViewSchoolLotFragment());
                    break;
                case R.id.menu_Notify:
                    vibe.vibrate(videRate);
                    replaceFragment(new NotifyFragment());
                    break;
                case R.id.menu_Persion:
                    vibe.vibrate(videRate);
                    replaceFragment(new PersionFragment());
                    break;
            }
            return true;
        });



        fragmentManager = getSupportFragmentManager();

        button = findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test", "back clicked");
                Fragment fragment = fragmentManager.findFragmentById(R.id.Frame_layout);
                if (fragment instanceof ViewSchoolLotFragment)
                {
                    vibe.vibrate(videRate);
                    replaceFragment(homePage);
                    BottomNavigationView mBottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavigationView);
                    mBottomNavigationView.getMenu().findItem(R.id.menu_Home).setChecked(true);
                }
                else if(fragment instanceof ViewBuilding)
                {
                    vibe.vibrate(videRate);
                    replaceFragment(new ViewSchoolLotFragment());
                }
                else if(fragment instanceof ViewBuildingLotFragment)
                {
                    vibe.vibrate(videRate);
                    replaceFragment(new ViewBuilding());
                }else if(fragment instanceof MyParkFragment)
                {
                    vibe.vibrate(videRate);
                    replaceFragment(homePage);
                }
                vibe.vibrate(videRate);
            }
        });


        BottomNavigationView mBottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.getMenu().findItem(R.id.menu_Home).setChecked(true);
    }

    public void ChangeToSchoolLot()
    {
        replaceFragment(new ViewSchoolLotFragment());
    }


    void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Frame_layout,fragment);
        fragmentTransaction.commit();
    }

}