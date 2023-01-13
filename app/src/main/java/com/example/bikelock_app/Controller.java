package com.example.bikelock_app;

import androidx.fragment.app.Fragment;

public class Controller {

    MainActivity mainActivity;

    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance()
    {
        if (instance == null)
        {
            instance = new Controller();
        }
        return instance;
    }

    public void changeFragment(Fragment fragment) {
        mainActivity.replaceFragment(fragment);
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
