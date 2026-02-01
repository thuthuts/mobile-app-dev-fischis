package de.hdmstuttgart.fischis.ui;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdmstuttgart.fischis.R;

import de.hdmstuttgart.fischis.databinding.ActivityMainBinding;
import de.hdmstuttgart.fischis.databinding.FragmentKilledFishBinding;
import de.hdmstuttgart.fischis.fish.FishData;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static BottomNavigationView bottomNavigation;
    private static FishData fishData;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        //set theme
        setTheme(R.style.Theme_Fischis);
        setContentView(view);

        bottomNavigation = binding.bottomNavigationView;
        fishData = new FishData();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.timerFragment, R.id.shopRecyclerViewFragment2, R.id.profileFragment)
                .build();

        NavigationUI.setupWithNavController(bottomNavigation, navController);
        Log.d("MainActivity", "Set up bottomNavigation");

        setStartFragment();
    }

    /**
     * Hide and show bottom navigation animations
     */
    public static void hideBottomNav() {
        bottomNavigation.clearAnimation();
        bottomNavigation.animate().translationY(bottomNavigation.getHeight()).setDuration(300);
        Log.d("MainActivity", "hide bottomNavigation");
    }

    public static void showBottomNav() {
        bottomNavigation.clearAnimation();
        bottomNavigation.animate().translationY(0).setDuration(300);
        Log.d("MainActivity", "show bottomNavigation");
    }


    /**
     * @return fishData so that it can be used in other fragments and the Fish are only instantiated ones
     */
    public static FishData getFishData() {
        return fishData;

    }

    public NavController getNavController() {
        return navController;
    }


    /**
     * loads timer fragment, when user already has logged in
     * loads KilledFishFragment, when user killed fish by swiping the app
     */
    private void setStartFragment() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (preferences.getBoolean("FishKilled", false)) {
            navController.navigate(R.id.killedFishFragment);
            hideBottomNav();
            Log.i("MainActivity", preferences.getBoolean("FishKilled", false) + "");

        } else if (preferences.getBoolean("LoggedIn", false)) {
            navController.navigate(R.id.action_toTimer);
            Log.i("MainActivity", "User already logged in. Go to Main view");
            Log.i("MainActivity", "Navigation to TimerFragment");
        }
    }
}