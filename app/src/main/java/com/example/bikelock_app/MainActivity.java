package com.example.bikelock_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bikelock_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new LocationFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.menu_Home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.menu_Location:
                    replaceFragment(new LocationFragment());
                    break;
                case R.id.menu_Notify:
                    replaceFragment(new NotifyFragment());
                    break;
                case R.id.menu_Persion:
                    replaceFragment(new PersionFragment());
                    break;
            }

            return true;
        });
    }

    void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Frame_layout,fragment);
        fragmentTransaction.commit();
    }

}